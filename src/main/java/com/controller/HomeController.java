package com.controller;

import com.model.Category;
import com.model.Price;
import com.model.Product;
import com.service.CategoryService;
import com.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String home(Model model) {
        List<Category> parentCategories = categoryService.getParentCategories();
        List<Category> childCategories = categoryService.getChildCategories();
        List<Product> products = productService.getAllProducts();

        LocalDate now = LocalDate.now();

        Map<Long, Integer> categoryProductCounts = new HashMap<>();
        for (Category category : parentCategories) {
            categoryProductCounts.put(category.getId(), categoryService.getCategoryProductCount(category));
        }

        model.addAttribute("parentCategories", parentCategories);
        model.addAttribute("childCategories", childCategories);
        model.addAttribute("products", products);
        model.addAttribute("now", now);
        model.addAttribute("categoryProductCounts", categoryProductCounts);
        return "home";
    }

}
