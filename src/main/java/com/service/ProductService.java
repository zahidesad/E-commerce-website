package com.service;

import com.model.Product;
import com.repository.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDAO productDAO;

    public int getNextProductId() {
        try {
            return productDAO.getNextProductId();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get next product ID", e);
        }
    }

    public boolean addProduct(int id, String name, String category, double price, String active) {
        try {
            return productDAO.addProduct(id, name, category, price, active);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add product", e);
        }
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try {
            return productDAO.getAllProducts();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public Product getProductById(int id) {
        return productDAO.getProductById(id);
    }

    public boolean updateProduct(int id, String name, String category, double price, String active) {
        return productDAO.updateProduct(id, name, category, price, active);
    }

    public List<Product> getActiveProducts() {
        return productDAO.getActiveProducts();
    }

    public List<Product> searchProducts(String search) {
        return productDAO.searchProducts(search);
    }
}
