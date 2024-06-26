package com.controller;

import com.model.Product;
import com.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/addNewProduct")
    public String showAddNewProductPage(Model model) {
        int nextProductId = productService.getNextProductId();
        model.addAttribute("productId", nextProductId);
        return "addNewProduct";
    }

    @PostMapping("/addNewProduct")
    public String addNewProduct(@RequestParam("name") String name,
                                @RequestParam("category") String category,
                                @RequestParam("price") double price,
                                @RequestParam("active") String active,
                                Model model) {
        int productId = productService.getNextProductId();
        boolean isAdded = productService.addProduct(productId, name, category, price, active);
        if (isAdded) {
            model.addAttribute("message", "Product Added Successfully!");
        } else {
            model.addAttribute("message", "Something went wrong! Try Again!");
        }
        model.addAttribute("productId", productId);
        return "addNewProduct";
    }

    @GetMapping("/allProductEditProduct")
    public String showAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "allProductEditProduct";
    }

    @GetMapping("/editProduct")
    public String showEditProductPage(@RequestParam("id") int id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "editProduct";
    }

    @PostMapping("/editProductAction")
    public String editProductAction(@RequestParam("id") int id,
                                    @RequestParam("name") String name,
                                    @RequestParam("category") String category,
                                    @RequestParam("price") double price,
                                    @RequestParam("active") String active,
                                    Model model) {
        boolean isUpdated = productService.updateProduct(id, name, category, price, active);
        if (isUpdated) {
            model.addAttribute("message", "Product Successfully Updated!");
        } else {
            model.addAttribute("message", "Something went wrong! Try Again!");
        }
        return "redirect:/allProductEditProduct";
    }
}


