package com.controller;

import com.model.Category;
import com.model.Price;
import com.model.Product;
import com.service.CategoryService;
import com.service.ProductService;
import com.service.SolrProductService;
import org.apache.solr.client.solrj.SolrServerException;
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
    private SolrProductService solrProductService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String home(Model model) throws SolrServerException, IOException {
        List<Category> parentCategories = categoryService.getParentCategories();
        List<Category> childCategories = categoryService.getChildCategories();
        List<Product> products = null;
        try {
            products = solrProductService.getAllProductsFromSolr();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
