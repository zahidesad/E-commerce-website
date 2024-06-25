package com.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/adminHome")
    public String adminHomePage(HttpSession session, Model model) {
        String email = (String) session.getAttribute("email");
        if (email == null || !"admin@gmail.com".equals(email)) {
            return "redirect:/login";
        }
        model.addAttribute("email", email);
        return "adminHome";
    }
}
