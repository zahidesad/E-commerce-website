package com.service;

import com.repository.UserDAO;
import com.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public User login(String email, String password) {
        User user = userDAO.findUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public boolean register(User user) {
        if (userDAO.findUserByEmail(user.getEmail()) == null) {
            return userDAO.saveUser(user);
        }
        return false;
    }
}

