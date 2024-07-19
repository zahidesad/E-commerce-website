package com.controller;

import com.model.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class VerifyController {

    @Autowired
    private UserService userService;

    @GetMapping("/verify/{code}")
    public String verifyUser(@PathVariable("code") String code, Model model) {
        Optional<User> userOptional = userService.findByVerificationCode(code);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            System.out.println("User found: " + user.getEmail()); // Debug Log
            user.setEnabled(true);
            user.setVerificationCode(null);
            userService.saveUser(user);
            model.addAttribute("msg", "Email verification successful!");
        } else {
            System.out.println("Invalid verification code."); // Debug Log
            model.addAttribute("msg", "Invalid verification code.");
        }
        return "verify_result";
    }

}
