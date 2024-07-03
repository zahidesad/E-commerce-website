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
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return "redirect:/login";
        }
        List<Cart> cartItems = cartService.getCartByEmail(email);
        model.addAttribute("cartItems", cartItems);

        int total = cartItems.stream().mapToInt(cart -> cart.getTotal()).sum();
        model.addAttribute("total", total);

        return "myCart";
    }


    @PostMapping("/addToCart")
    public String addToCart(@RequestParam("productId") int productId, @RequestParam("quantity") int quantity, HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return "redirect:/login";
        }

        Cart cart = new Cart();
        cart.setEmail(email);
        cart.setProductId(productId);
        cart.setQuantity(quantity);

        Optional<Product> product = productService.getProductById((long) productId);
        if (product.isPresent()) {
            cart.setPrice(product.get().getPrice());
        } else {

            return "redirect:/home?error=ProductNotFound";
        }

        cartService.addCart(cart);

        return "redirect:/myCart";
    }

    @GetMapping("/incDecQuantity")
    public String incDecQuantity(@RequestParam("id") int id, @RequestParam("quantity") String quantity) {
        cartService.updateCartQuantity(id, quantity);
        return "redirect:/myCart";
    }

    @GetMapping("/removeFromCart")
    public String removeFromCart(@RequestParam("id") int id) {
        cartService.removeFromCart(id);
        return "redirect:/myCart";
    }
}
