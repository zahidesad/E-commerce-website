package com.controller;

import com.model.Product;
import com.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

@Controller
public class ProductImageServlet extends HttpServlet {

    @Autowired
    private ProductService productService;

    @GetMapping("/productImage")
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long productId = Long.valueOf(request.getParameter("id"));
        Optional<Product> product = productService.getProductById(productId);

        if (product.isPresent() && product.get().getPhotoData() != null) {
            response.setContentType("image/jpeg");
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(product.get().getPhotoData());
            outputStream.close();
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404
        }
    }
}
