package com.service;

import com.model.Category;
import com.model.Price;
import com.model.Product;
import com.repository.CategoryRepository;
import com.repository.PriceRepository;
import com.repository.ProductRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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

    @Transactional
    public void saveProduct(Product product, BigDecimal price, Date startDate, Date endDate, List<Long> categoryIds) {
        List<Category> categories = categoryRepository.findAllById(categoryIds);
        product.setCategories(categories);
        productRepository.save(product);

        Price productPrice = new Price();
        productPrice.setProduct(product);
        productPrice.setPrice(price);
        productPrice.setStartDate(startDate);
        productPrice.setEndDate(endDate);
        priceRepository.save(productPrice);
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
        });
        return product;
    }


    public List<Price> getPricesByProductId(Long productId) {
        return priceRepository.findByProductId(productId);
    }

    @Transactional
    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProductById(Long id) {
        cartService.deleteCartItemByProductId(id); // First delete product from cart
        productRepository.deleteById(id); // Then, delete product
    }

    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContainingIgnoreCase(query);
    }

    @Transactional(readOnly = true)
    public List<Product> getProductsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (category != null) {
            Hibernate.initialize(category.getProducts());
            Set<Product> products = new HashSet<>(category.getProducts());
            for (Category childCategory : category.getChildCategories()) {
                Hibernate.initialize(childCategory.getProducts());
                products.addAll(childCategory.getProducts());
            }
            return products.stream().distinct().collect(Collectors.toList());
        }
        return List.of();
    }


        private List<Price> getCurrentPrices(List<Price> prices) {
        Date currentDate = new Date();
        return prices.stream()
                .filter(price -> !price.getStartDate().after(currentDate) && (price.getEndDate() == null || !price.getEndDate().before(currentDate)))
                .collect(Collectors.toList());
    }

}
