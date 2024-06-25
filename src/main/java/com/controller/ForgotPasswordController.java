package com.controller;

import com.forms.ForgotPasswordForm;
import com.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @GetMapping("/forgotPassword")
    public String forgotPasswordPage(Model model) {
        model.addAttribute("forgotPasswordForm", new ForgotPasswordForm());
        return "forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String forgotPassword(ForgotPasswordForm forgotPasswordForm, Model model) {
        boolean isPasswordReset = forgotPasswordService.resetPassword(forgotPasswordForm);
        if (isPasswordReset) {
            return "redirect:/forgotPassword?msg=done";
        } else {
            return "redirect:/forgotPassword?msg=invalid";
        }
    }
}
