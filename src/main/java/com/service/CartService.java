package com.service;

import com.repository.CartDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartDAO cartDAO;

    public boolean addToCart(String email, int productId) {
        return cartDAO.addToCart(email, productId);
    }
}
