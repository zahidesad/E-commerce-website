package com.repository;

import com.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndMobileNumberAndSecurityQuestionAndAnswer(String email, String mobileNumber, String securityQuestion, String answer);
    Optional<User> findByVerificationCode(String verificationCode);
}
