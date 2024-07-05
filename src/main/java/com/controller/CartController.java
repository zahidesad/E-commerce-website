package com.controller;

import com.model.Cart;
import com.model.Product;
import com.service.CartService;
import com.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @GetMapping("/myCart")
    public String viewCart(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        Cart cart = cartService.getOrCreateCartByUserId(userId);
        model.addAttribute("cart", cart);

        double total = cart.getCartItems().stream().mapToDouble(item -> item.getTotal().doubleValue()).sum();
        model.addAttribute("total", total);

        return "myCart";
    }

    @PostMapping("/addToCart")
    public String addToCart(@RequestParam("productId") Long productId, @RequestParam("quantity") int quantity, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        Cart cart = cartService.getOrCreateCartByUserId(userId);
        cartService.addProductToCart(cart, productId, quantity);

        return "redirect:/myCart";
    }

    @GetMapping("/incDecQuantity")
    public String incDecQuantity(@RequestParam("id") Long id, @RequestParam("quantity") String quantity) {
        cartService.updateCartItemQuantity(id, quantity);
        return "redirect:/myCart";
    }

    @GetMapping("/removeFromCart")
    public String removeFromCart(@RequestParam("id") Long id) {
        cartService.removeFromCart(id);
        return "redirect:/myCart";
    }
}
