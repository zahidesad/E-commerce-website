package com.repository;

import com.connection.ConnectionProvider;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@Repository
public class CartDAO {

    public boolean addToCart(String email, int productId) {
        boolean isExist = false;
        int quantity = 1;
        int productPrice = 0;
        int productTotal = 0;
        int cartTotal = 0;

        try {
            Connection con = ConnectionProvider.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM product WHERE id = '" + productId + "'");
            if (rs.next()) {
                productPrice = rs.getInt("price");
                productTotal = productPrice;
            }
            ResultSet rs2 = statement.executeQuery("SELECT * FROM cart WHERE product_id = '" + productId + "' AND email = '" + email + "' AND address IS NULL");
            if (rs2.next()) {
                cartTotal = rs2.getInt("total");
                cartTotal += productTotal;
                quantity = rs2.getInt("quantity");
                quantity++;
                isExist = true;
            }
            if (isExist) {
                statement.executeUpdate("UPDATE cart SET total = '" + cartTotal + "', quantity = '" + quantity + "' WHERE product_id = '" + productId + "' AND email = '" + email + "' AND address IS NULL");
                return false;
            } else {
                PreparedStatement ps = con.prepareStatement("INSERT INTO cart(email, product_id, quantity, price, total) VALUES(?, ?, ?, ?, ?)");
                ps.setString(1, email);
                ps.setInt(2, productId);
                ps.setInt(3, quantity);
                ps.setInt(4, productPrice);
                ps.setInt(5, productTotal);
                ps.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
