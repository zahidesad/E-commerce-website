package com.controller;

import com.forms.LoginForm;
import com.model.User;
import com.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String login(LoginForm loginForm, HttpSession session, Model model) {
        User user = userService.login(loginForm.getEmail(), loginForm.getPassword());
        if (user != null) {
            session.setAttribute("email", user.getEmail());
            if ("admin@gmail.com".equals(user.getEmail()) && "admin".equals(user.getPassword())) {
                return "redirect:/adminHome";
            }
            return "redirect:/home";
        }
        model.addAttribute("msg", "invalid");
        return "login";
    }
}
