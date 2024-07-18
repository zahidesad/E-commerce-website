package com.controller;

import com.forms.RegisterForm;
import com.model.User;
import com.service.TCNumberVerificationService;
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

    @Autowired
    private TCNumberVerificationService TCNumberVerificationService;

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "signup";
    }

    @PostMapping("/register")
    public String register(RegisterForm registerForm, Model model) {
        boolean isVerified = TCNumberVerificationService.verify(
                registerForm.getTcNumber(),
                registerForm.getName(),
                registerForm.getSurname(),
                registerForm.getBirthYear());

        System.out.println("Verification Result: " + isVerified);

        if (!isVerified) {
            model.addAttribute("msg", "invalid_tc");
            return "signup";
        }

        User user = new User();
        user.setName(registerForm.getName());
        user.setSurname(registerForm.getSurname());
        user.setEmail(registerForm.getEmail());
        user.setMobileNumber(registerForm.getMobileNumber());
        user.setSecurityQuestion(registerForm.getSecurityQuestion());
        user.setAnswer(registerForm.getAnswer());
        user.setPassword(registerForm.getPassword());
        user.setTcNumber(registerForm.getTcNumber());
        user.setBirthYear(registerForm.getBirthYear());
        user.setEnabled(true);
        user.setRole("ROLE_USER");

        if (userService.register(user) != null) {
            model.addAttribute("msg", "valid");
            return "redirect:/login";
        }
        model.addAttribute("msg", "invalid");
        return "signup";
    }
}
