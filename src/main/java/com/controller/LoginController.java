package com.controller;

import com.forms.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String loginPage(final Model model) {
        model.addAttribute("loginForm" , new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String login(final Model model , LoginForm loginForm) {
        return "home";
    }
}
