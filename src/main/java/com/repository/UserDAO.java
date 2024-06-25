package com.repository;

import com.model.User;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class UserDAO {

    @Autowired
    private DataSource dataSource;

    public User findUserByEmail(String email) {
        User user = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE email = ?")) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setMobileNumber(rs.getString("mobileNumber"));
                user.setSecurityQuestion(rs.getString("securityQuestion"));
                user.setAnswer(rs.getString("answer"));
                user.setPassword(rs.getString("password"));
                user.setAddress(rs.getString("address"));
                user.setCity(rs.getString("city"));
                user.setState(rs.getString("state"));
                user.setCountry(rs.getString("country"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean saveUser(User user) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO users (name, email, mobileNumber, securityQuestion, answer, password, address, city, state, country) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getMobileNumber());
            ps.setString(4, user.getSecurityQuestion());
            ps.setString(5, user.getAnswer());
            ps.setString(6, user.getPassword());
            ps.setString(7, user.getAddress());
            ps.setString(8, user.getCity());
            ps.setString(9, user.getState());
            ps.setString(10, user.getCountry());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUserPassword(String email, String newPassword) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement("UPDATE users SET password = ? WHERE email = ?")) {
            ps.setString(1, newPassword);
            ps.setString(2, email);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
