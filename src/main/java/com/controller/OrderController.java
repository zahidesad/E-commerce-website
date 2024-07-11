package com.controller;

import com.model.Cart;
import com.service.CartService;
import com.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/completeOrder")
    public String completeOrder(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Cart cart = cartService.getOrCreateCartByUserId(userId);
        orderService.createOrderFromCart(cart);

        cartService.clearCartByUserId(userId);

        return "redirect:/orderConfirmation";
    }
}
