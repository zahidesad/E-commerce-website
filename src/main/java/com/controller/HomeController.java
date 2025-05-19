package com.controller;

import com.model.Category;
import com.model.Product;
import com.service.CategoryService;
import com.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String home(Model model) throws  IOException {
        List<Category> parent = categoryService.getParentCategories();
        List<Category> child  = categoryService.getChildCategories();
        List<Product> products = productService.getAllProducts();   // <-- JPA

        Map<Long,Integer> counts = new HashMap<>();
        parent.forEach(c -> counts.put(c.getId(), categoryService.getCategoryProductCount(c)));

        model.addAttribute("parentCategories", parent);
        model.addAttribute("childCategories",  child);
        model.addAttribute("products",         products);
        model.addAttribute("now",              LocalDate.now());
        model.addAttribute("categoryProductCounts", counts);
        return "home";
    }

}
