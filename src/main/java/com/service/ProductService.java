package com.service;

import com.model.Category;
import com.model.Price;
import com.model.Product;
import com.model.Stock;
import com.repository.CategoryRepository;
import com.repository.PriceRepository;
import com.repository.ProductRepository;
import com.repository.StockRepository;
import org.apache.solr.client.solrj.SolrServerException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;


@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private SolrIndexingService solrIndexingService;

    @Autowired
    private SolrProductService solrProductService;

    @Transactional
    public void saveProduct(Product product, BigDecimal price, int quantity, Date startDate, Date endDate, List<Long> categoryIds) {
        List<Category> categories = categoryRepository.findAllById(categoryIds);
        product.setCategories(categories);
        productRepository.save(product);

        Price productPrice = new Price();
        productPrice.setProduct(product);
        productPrice.setPrice(price);
        productPrice.setStartDate(startDate);
        productPrice.setEndDate(endDate);
        List<Price> prices = new ArrayList<>();
        prices.add(productPrice);
        product.setPrices(prices);

        priceRepository.save(productPrice);

        Stock stock = new Stock();
        stock.setProduct(product);
        stock.setQuantity(quantity);
        stockRepository.save(stock);

        product.getStocks().add(stock);
        try {
            solrIndexingService.indexProduct(product);
        } catch (IOException | SolrServerException e) {
            e.printStackTrace();
        }
    }


    @Transactional
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        products.forEach(product -> {
            Hibernate.initialize(product.getCategories());
            Hibernate.initialize(product.getPrices());
        });
        return products;
    }

    @Transactional
    public Optional<Product> getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        product.ifPresent(p -> {
            Hibernate.initialize(p.getCategories());
            Hibernate.initialize(p.getPrices());
            Hibernate.initialize(p.getStocks());
        });
        return product;
    }

    @Transactional
    public void updateProduct(Product updatedProduct, Long categoryId, String active, int quantity, List<Long> priceIds, List<BigDecimal> priceAmounts, List<Date> startDates, List<Date> endDates) {
        Optional<Product> existingProductOpt = productRepository.findById(updatedProduct.getId());
        if (existingProductOpt.isPresent()) {
            Product existingProduct = existingProductOpt.get();

            // Update only the specified fields
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setActive(active);

            // Update category
            Optional<Category> category = categoryRepository.findById(categoryId);
            category.ifPresent(value -> existingProduct.setCategories(new ArrayList<>(List.of(value))));

            // Update quantity
            List<Stock> stocks = existingProduct.getStocks();
            Stock stock;
            if (stocks == null || stocks.isEmpty()) {
                stock = new Stock();
                stock.setProduct(existingProduct);
                stock.setQuantity(quantity);
                stocks.add(stock);
            } else {
                stock = stocks.get(0); // Assuming single stock record per product
                stock.setQuantity(quantity);
            }
            stockRepository.save(stock);

            // Ensure prices are loaded
            List<Price> existingPrices = new ArrayList<>(existingProduct.getPrices());
            if (existingPrices == null) {
                existingPrices = new ArrayList<>();
                existingProduct.setPrices(existingPrices);
            }

            // Track updated prices
            List<Long> updatedPriceIds = new ArrayList<>();

            // Update existing prices and add new ones
            for (int i = 0; i < priceIds.size(); i++) {
                Long priceId = priceIds.get(i);
                boolean priceFound = false;

                for (Price price : existingPrices) {
                    if (price.getId().equals(priceId)) {
                        price.setPrice(priceAmounts.get(i));
                        price.setStartDate(startDates.get(i));
                        price.setEndDate(endDates.get(i));
                        updatedPriceIds.add(priceId);
                        priceFound = true;
                        break;
                    }
                }

                // If price not found, it's a new price
                if (!priceFound) {
                    Price newPrice = new Price();
                    newPrice.setPrice(priceAmounts.get(i));
                    newPrice.setStartDate(startDates.get(i));
                    newPrice.setEndDate(endDates.get(i));
                    newPrice.setProduct(existingProduct);
                    priceRepository.save(newPrice); // Save new price to ensure ID is generated
                    existingPrices.add(newPrice);
                    updatedPriceIds.add(newPrice.getId()); // Add the new price ID to the updated list
                }
            }

            // Remove prices that are no longer present
            existingPrices.removeIf(price -> !updatedPriceIds.contains(price.getId()));

            productRepository.save(existingProduct);

            try {
                solrIndexingService.indexProduct(existingProduct);
            } catch (IOException | SolrServerException e) {
                e.printStackTrace();
            }
        }
    }



    public void deleteProductById(Long id) {
        cartService.deleteCartItemByProductId(id); // First delete product from cart
        productRepository.deleteById(id); // Then, delete product

        try {
            solrIndexingService.deleteProduct(id);
        } catch (IOException | SolrServerException e) {
            e.printStackTrace();
        }

    }

    public List<Product> searchProducts(String query) {
        try {
            return solrProductService.searchProductsInSolr(query);
        } catch (IOException | SolrServerException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Transactional
    public boolean isProductInStock(Long productId) {
        Optional<Product> productOpt = getProductById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            int totalStock = product.getStocks().stream()
                    .mapToInt(Stock::getQuantity)
                    .sum();
            return totalStock > 0;
        }
        return false;
    }

    @Transactional
    public void indexAllProductsToSolr() {
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            try {
                solrIndexingService.indexProduct(product);
            } catch (IOException | SolrServerException e) {
                e.printStackTrace();
            }
        }
    }

}