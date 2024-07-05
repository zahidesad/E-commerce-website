package com.controller;

import com.model.Category;
import com.model.Product;
import com.service.CategoryService;
import com.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

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
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "addNewProduct";
    }

    @PostMapping("/addNewProduct")
    public String addNewProduct(@ModelAttribute("product") Product product,
                                @RequestParam("price") BigDecimal price,
                                @RequestParam("category") List<Long> categoryIds,
                                @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        productService.saveProduct(product, price, startDate, endDate, categoryIds);
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
        // productService.saveProduct(product); TODO: Burayı değiştirmeyi unutma
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
