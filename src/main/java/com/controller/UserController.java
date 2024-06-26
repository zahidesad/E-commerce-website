package com.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @GetMapping("/home")
    public String showHomePage(Model model, HttpSession session) {
        List<Product> products = productService.getActiveProducts();
        model.addAttribute("products", products);
        return "home";
    }

    @PostMapping("/searchHome")
    public String searchHome(@RequestParam("search") String search, Model model) {
        List<Product> products = productService.searchProducts(search);
        model.addAttribute("products", products);
        if (products.isEmpty()) {
            model.addAttribute("message", "Nothing to show");
        }
        return "searchHome";
    }

    @GetMapping("/addToCart")
    public String addToCart(@RequestParam("id") int productId, HttpSession session, RedirectAttributes redirectAttributes) {
        String email = (String) session.getAttribute("email");
        boolean isAdded = cartService.addToCart(email, productId);
        if (isAdded) {
            redirectAttributes.addFlashAttribute("message", "Product added successfully!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Product already exist in your cart! Quantity increased!");
        }
        return "redirect:/home";
    }
}