package com.controller;

import com.model.Product;
import com.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private ProductService productService;

    @GetMapping("/adminHome")
    public String adminHomePage(HttpSession session, Model model) {
        String email = (String) session.getAttribute("email");
        if (email == null || !"admin@gmail.com".equals(email)) {
            return "redirect:/login";
        }
        model.addAttribute("email", email);
        return "adminHome";
    }

    @GetMapping("/addNewProduct")
    public String addNewProductPage(Model model) {
        model.addAttribute("product", new Product());
        return "addNewProduct";
    }

    @PostMapping("/addNewProduct")
    public String addNewProduct(@ModelAttribute("product") Product product) {
        productService.saveProduct(product);
        return "redirect:/allProductEditProduct";
    }

    @GetMapping("/allProductEditProduct")
    public String allProductEditProductPage(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "allProductEditProduct";
    }

    @GetMapping("/editProduct")
    public String editProductPage(@RequestParam("id") int id, Model model) {
        Optional<Product> product = productService.getProductById((long) id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            return "editProduct";
        }
        return "redirect:/allProductEditProduct";
    }

    @PostMapping("/editProduct")
    public String editProduct(@ModelAttribute("product") Product product) {
        productService.saveProduct(product);
        return "redirect:/allProductEditProduct";
    }

    @PostMapping("/updateProduct")
    public String updateProduct(@ModelAttribute("product") Product product) {
        productService.updateProduct(product);
        return "redirect:/allProductEditProduct";
    }

    @PostMapping("/deleteProduct")
    public String deleteProduct(@RequestParam("productId") Long productId) {
        productService.deleteProductById(productId);
        return "redirect:/allProductEditProduct";
    }
}
