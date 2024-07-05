package com.controller;

import com.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoryRelationshipController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/manageCategoryRelationships")
    public String manageCategoryRelationshipsPage(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categoryRelationship";
    }

    @PostMapping("/manageCategoryRelationship")
    public String manageCategoryRelationships(@RequestParam("parentCategory") Long parentCategoryId,
                                              @RequestParam("childCategory") Long childCategoryId) {
        categoryService.setCategoryRelationship(parentCategoryId, childCategoryId);
        return "redirect:/manageCategoryRelationships";
    }
}
