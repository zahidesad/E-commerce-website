package com.controller;

import com.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/addCategory")
    public String addCategoryPage(Model model) {
        return "addCategory";
    }

    @PostMapping("/addCategory")
    public String addCategory(@RequestParam("name") String name) {
        categoryService.addCategory(name);
        return "redirect:/addCategory";
    }
}
