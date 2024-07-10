package com.controller;

import com.model.Category;
import com.model.Product;
import com.repository.CategoryRepository;
import com.service.CategoryService;
import com.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/addCategory")
    public String addCategoryPage(Model model) {
        return "addCategory";
    }

    @PostMapping("/addCategory")
    public String addCategory(@RequestParam("name") String name) {
        categoryService.addCategory(name);
        return "redirect:/addCategory";
    }

    @GetMapping("/categoryProducts")
    public String categoryProducts(@RequestParam("categoryId") Long categoryId, Model model) {
        List<Product> products = productService.getProductsByCategory(categoryId);
        model.addAttribute("products", products);
        return "categoryProducts";
    }

    @GetMapping("/productDetails")
    public String productDetails(@RequestParam("id") Long productId, Model model) {
        Optional<Product> product = productService.getProductById(productId);
        product.ifPresent(value -> model.addAttribute("products", value));
        return "productDetails";
    }



}
