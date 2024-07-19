package com.controller;

import com.forms.RegisterForm;
import com.model.User;
import com.service.EmailService;
import com.service.TCNumberVerificationService;
import com.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private TCNumberVerificationService TCNumberVerificationService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "signup";
    }

    @PostMapping("/register")
    public String register(RegisterForm registerForm, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
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

        if (userService.isEmailAlreadyInUse(registerForm.getEmail())) {
            model.addAttribute("msg", "email_in_use");
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
        user.setEnabled(false);
        user.setRole("ROLE_USER");

        String verificationCode = UUID.randomUUID().toString();
        user.setVerificationCode(verificationCode);

        if (userService.register(user) != null) {
            String siteURL = request.getRequestURL().toString().replace(request.getServletPath(), "");
            try {
                emailService.sendVerificationEmail(user, siteURL);
            } catch (MessagingException e) {
                e.printStackTrace();
                model.addAttribute("msg", "email_send_error");
                return "signup";
            }
            redirectAttributes.addFlashAttribute("msg", "Registration successful! Please confirm your email address.");
            return "redirect:/login";
        }
        model.addAttribute("msg", "invalid");
        return "signup";
    }
}
