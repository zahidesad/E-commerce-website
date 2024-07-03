package com.controller;

import com.forms.RegisterForm;
import com.model.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "signup";
    }

    @PostMapping("/register")
    public String register(RegisterForm registerForm, Model model) {
        User user = new User();
        user.setName(registerForm.getName());
        user.setEmail(registerForm.getEmail());
        user.setMobileNumber(registerForm.getMobileNumber());
        user.setSecurityQuestion(registerForm.getSecurityQuestion());
        user.setAnswer(registerForm.getAnswer());
        user.setPassword(registerForm.getPassword());
        user.setAddress("");
        user.setCity("");
        user.setState("");
        user.setCountry("");

        if (userService.register(user) != null) {
            model.addAttribute("msg", "valid");
            return "redirect:/login";
        }
        model.addAttribute("msg", "invalid");
        return "signup";
    }
}
