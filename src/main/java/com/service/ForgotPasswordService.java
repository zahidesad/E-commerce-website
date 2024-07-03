package com.service;

import com.repository.UserRepository;
import com.model.User;
import com.forms.ForgotPasswordForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForgotPasswordService {

    @Autowired
    private UserRepository userRepository;

    public boolean resetPassword(ForgotPasswordForm form) {
        Optional<User> optionalUser = userRepository.findByEmailAndMobileNumberAndSecurityQuestionAndAnswer(
                form.getEmail(),
                form.getMobileNumber(),
                form.getSecurityQuestion(),
                form.getAnswer()
        );

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(form.getNewPassword());
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
