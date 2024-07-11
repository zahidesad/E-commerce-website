package com.controller;

import com.model.CartItem;
import com.model.Category;
import com.model.Price;
import com.model.Product;
import com.repository.PriceRepository;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PriceRepository priceRepository;

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
                                @RequestParam("quantity") int quantity,
                                @RequestParam("category") List<Long> categoryIds,
                                @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
                                @RequestParam("photo") MultipartFile photo) {
        // Fotoğrafı işleme
        if (!photo.isEmpty()) {
            try {
                byte[] bytes = photo.getBytes();
                String photoName = photo.getOriginalFilename();

                String uploadDir = "C:/Users/zahid/IdeaProjects/E-commerce-website/src/main/webapp/images";

                Path path = Paths.get(uploadDir + File.separator + photoName);
                Files.write(path, bytes);

                product.setPhotoName(photoName);
                product.setPhotoData(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        productService.saveProduct(product, price, quantity, startDate, endDate, categoryIds);
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
                                @RequestParam("quantity") int quantity,
                                @RequestParam("priceId") List<Long> priceIds,
                                @RequestParam("priceAmount") List<BigDecimal> priceAmounts,
                                @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) List<Date> startDates,
                                @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) List<Date> endDates) {
        productService.updateProduct(product, categoryId, active, quantity, priceIds, priceAmounts, startDates, endDates);
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
            Product existingProduct = product.get();
            Price newPrice = new Price();
            newPrice.setProduct(existingProduct);
            newPrice.setPrice(price);
            newPrice.setStartDate(startDate);
            newPrice.setEndDate(endDate);

            // Save the new price first to generate the ID
            priceRepository.save(newPrice);

            existingProduct.getPrices().add(newPrice);

            // Save the product after the new price has been saved
            productService.updateProduct(existingProduct,
                    existingProduct.getCategories().get(0).getId(),
                    existingProduct.getActive(),
                    existingProduct.getStocks().get(0).getQuantity(),
                    existingProduct.getPrices().stream().map(Price::getId).collect(Collectors.toList()),
                    existingProduct.getPrices().stream().map(Price::getPrice).collect(Collectors.toList()),
                    existingProduct.getPrices().stream().map(Price::getStartDate).collect(Collectors.toList()),
                    existingProduct.getPrices().stream().map(Price::getEndDate).collect(Collectors.toList()));
        }
        return "redirect:/allProductEditProduct";
    }
}
