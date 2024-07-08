package com.controller;

import com.model.CartItem;
import com.model.Category;
import com.model.Price;
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
import java.util.*;

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
        try {
            model.addAttribute("products", productService.getAllProducts());
            return "allProductEditProduct";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/editProduct")
    public String editProductPage(@RequestParam("id") int id, Model model) {
        Optional<Product> product = productService.getProductById((long) id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "editProduct";
        }
        return "redirect:/allProductEditProduct";
    }


    @PostMapping("/updateProduct")
    public String updateProduct(@ModelAttribute("product") Product product,
                                @RequestParam("categoryId") Long categoryId,
                                @RequestParam("active") String active,
                                @RequestParam("priceId") List<Long> priceIds,
                                @RequestParam("priceAmount") List<BigDecimal> priceAmounts,
                                @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) List<Date> startDates,
                                @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) List<Date> endDates) {

        // Update category
        Optional<Category> category = categoryService.getCategoryById(categoryId);
        category.ifPresent(value -> product.setCategories(List.of(value)));

        // Update active status
        product.setActive(active);

        // Load existing prices
        List<Price> existingPrices = productService.getPricesByProductId(product.getId());
        if (existingPrices == null) {
            existingPrices = new ArrayList<>();
        }
        product.setPrices(existingPrices);

        // Track updated prices
        List<Long> updatedPriceIds = new ArrayList<>();

        // Update existing prices and add new ones
        for (int i = 0; i < priceIds.size(); i++) {
            Long priceId = priceIds.get(i);
            boolean priceFound = false;

            for (Price price : existingPrices) {
                if (price.getId().equals(priceId)) {
                    price.setPrice(priceAmounts.get(i));
                    price.setStartDate(startDates.get(i));
                    price.setEndDate(endDates.get(i));
                    updatedPriceIds.add(priceId);
                    priceFound = true;
                    break;
                }
            }

            // If price not found, it's a new price
            if (!priceFound) {
                Price newPrice = new Price();
                newPrice.setPrice(priceAmounts.get(i));
                newPrice.setStartDate(startDates.get(i));
                newPrice.setEndDate(endDates.get(i));
                newPrice.setProduct(product);
                existingPrices.add(newPrice);
            }
        }
        // Remove prices that are no longer present
        existingPrices.removeIf(price -> !updatedPriceIds.contains(price.getId()));

        // Ensure cartItems list is not null
        if (product.getCartItems() == null) {
            product.setCartItems(new ArrayList<>());
        }

        // Debug log for checking prices
        for (Price price : product.getPrices()) {
            System.out.println("Price ID: " + price.getId() + ", Amount: " + price.getPrice() +
                    ", Start Date: " + price.getStartDate() + ", End Date: " + price.getEndDate());
        }

        productService.updateProduct(product);
        return "redirect:/allProductEditProduct";
    }

    @PostMapping("/deleteProduct")
    public String deleteProduct(@RequestParam("productId") Long productId) {
        productService.deleteProductById(productId);
        return "redirect:/allProductEditProduct";
    }

    @GetMapping("/addNewPrice")
    public String addNewPricePage(@RequestParam("id") Long id, Model model) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            model.addAttribute("price", new Price());
            return "addNewPrice";
        }
        return "redirect:/allProductEditProduct";
    }

    @PostMapping("/addNewPrice")
    public String addNewPrice(@RequestParam("id") Long id,
                              @RequestParam("price") BigDecimal price,
                              @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                              @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            Price newPrice = new Price();
            newPrice.setProduct(product.get());
            newPrice.setPrice(price);
            newPrice.setStartDate(startDate);
            newPrice.setEndDate(endDate);
            product.get().getPrices().add(newPrice);
            productService.updateProduct(product.get());
        }
        return "redirect:/allProductEditProduct";
    }
}
