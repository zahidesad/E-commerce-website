package com.repository;

import com.connection.ConnectionProvider;
import com.model.Product;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CartDAO {

    public boolean addToCart(String email, int productId) {
        try {
            Connection connection = ConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM cart WHERE email = ? AND product_id = ? AND address IS NULL");
            statement.setString(1, email);
            statement.setInt(2, productId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int quantity = resultSet.getInt("quantity") + 1;
                double total = resultSet.getDouble("total") + resultSet.getDouble("price");
                statement = connection.prepareStatement("UPDATE cart SET quantity = ?, total = ? WHERE email = ? AND product_id = ? AND address IS NULL");
                statement.setInt(1, quantity);
                statement.setDouble(2, total);
                statement.setString(3, email);
                statement.setInt(4, productId);
                statement.executeUpdate();
                return false;
            } else {
                statement = connection.prepareStatement("INSERT INTO cart (email, product_id, quantity, price, total) VALUES (?, ?, ?, ?, ?)");
                statement.setString(1, email);
                statement.setInt(2, productId);
                statement.setInt(3, 1);
                statement.setDouble(4, getProductPrice(productId));
                statement.setDouble(5, getProductPrice(productId));
                statement.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Product> getCartProducts(String email) {
        List<Product> products = new ArrayList<>();
        try {
            Connection connection = ConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT product.*, cart.quantity, cart.total FROM product INNER JOIN cart ON product.id = cart.product_id WHERE cart.email = ? AND cart.address IS NULL");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setCategory(resultSet.getString("category"));
                product.setPrice(resultSet.getDouble("price"));
                product.setQuantity(resultSet.getInt("quantity"));
                product.setTotal(resultSet.getDouble("total"));
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public int getCartTotal(String email) {
        int total = 0;
        try {
            Connection connection = ConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT SUM(total) FROM cart WHERE email = ? AND address IS NULL");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    public String updateCartQuantity(String email, int productId, String incDec) {
        try {
            Connection connection = ConnectionProvider.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM cart WHERE email='" + email + "' AND product_id = '" + productId + "' AND address IS NULL");
            double price = 0;
            double total = 0;
            int quantity = 0;
            if (resultSet.next()) {
                price = resultSet.getDouble("price");
                total = resultSet.getDouble("total");
                quantity = resultSet.getInt("quantity");
            }
            if (quantity == 1 && "dec".equals(incDec)) {
                return "There is only one Quantity! So click on remove!";
            } else if (quantity != 1 && "dec".equals(incDec)) {
                total -= price;
                quantity--;
                statement.executeUpdate("UPDATE cart SET total= '" + total + "', quantity = '" + quantity + "' WHERE email = '" + email + "' AND product_id = '" + productId + "' AND address IS NULL");
                return "Quantity Decreased Successfully!";
            } else {
                total += price;
                quantity++;
                statement.executeUpdate("UPDATE cart SET total= '" + total + "', quantity = '" + quantity + "' WHERE email = '" + email + "' AND product_id = '" + productId + "' AND address IS NULL");
                return "Quantity Increased Successfully!";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Something went wrong!";
        }
    }

    public void removeFromCart(String email, int productId) {
        try {
            Connection connection = ConnectionProvider.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM cart WHERE email = '" + email + "' AND product_id = '" + productId + "' AND address IS NULL");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double getProductPrice(int productId) {
        double price = 0;
        try {
            Connection connection = ConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT price FROM product WHERE id = ?");
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                price = resultSet.getDouble("price");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return price;
    }
}
