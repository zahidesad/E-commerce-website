package com.controller;

import com.model.Address;
import com.model.Cart;
import com.service.AddressService;
import com.service.CartService;
import com.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private AddressService addressService;

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
        System.out.println("removeFromCart method called with id: " + id);
        cartService.removeFromCart(id);
        System.out.println("Product removed from cart successfully.");
        return "redirect:/myCart";
    }

    @GetMapping("/proceedToOrder")
    public String proceedToOrder(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        List<Address> addresses = addressService.getAddressesByUserId(userId);
        if (addresses == null || addresses.isEmpty()) {
            return "redirect:/myAddress";
        } else {
            return "redirect:/myOrderDetails";
        }
    }

}
