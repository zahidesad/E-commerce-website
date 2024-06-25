package com.service;

import com.repository.UserDAO;
import com.model.User;
import com.forms.ForgotPasswordForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordService {

    @Autowired
    private UserDAO userDAO;

    public boolean resetPassword(ForgotPasswordForm form) {
        User user = userDAO.findUserByEmail(form.getEmail());
        if (user != null &&
                user.getMobileNumber().equals(form.getMobileNumber()) &&
                user.getSecurityQuestion().equals(form.getSecurityQuestion()) &&
                user.getAnswer().equals(form.getAnswer())) {
            return userDAO.updateUserPassword(form.getEmail(), form.getNewPassword());
        }
        return false;
    }
}
