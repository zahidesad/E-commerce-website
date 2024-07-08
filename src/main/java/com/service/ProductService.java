package com.service;

import com.model.Category;
import com.model.Price;
import com.model.Product;
import com.repository.CategoryRepository;
import com.repository.PriceRepository;
import com.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
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

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContainingIgnoreCase(query);
    }
}
