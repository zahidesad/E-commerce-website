package com.service;

import com.model.Product;
import com.repository.CartDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartDAO cartDAO;

    public boolean addToCart(String email, int productId) {
        return cartDAO.addToCart(email, productId);
    }

    public List<Product> getCartProducts(String email) {
        return cartDAO.getCartProducts(email);
    }

    public int getCartTotal(String email) {
        return cartDAO.getCartTotal(email);
    }

    public String updateCartQuantity(String email, int productId, String incDec) {
        return cartDAO.updateCartQuantity(email, productId, incDec);
    }

    public void removeFromCart(String email, int productId) {
        cartDAO.removeFromCart(email, productId);
    }
}
