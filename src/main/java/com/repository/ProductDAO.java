package com.repository;

import com.connection.ConnectionProvider;
import com.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDAO {

    @Autowired
    private DataSource dataSource;

    public int getNextProductId() throws Exception {
        try (Connection con = dataSource.getConnection()) {
            String sql = "SELECT MAX(id) FROM product";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) + 1;
            } else {
                return 1;
            }
        }
    }

    public boolean addProduct(int id, String name, String category, double price, String active) throws Exception {
        try (Connection con = dataSource.getConnection()) {
            String sql = "INSERT INTO product VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, category);
            stmt.setDouble(4, price);
            stmt.setString(5, active);
            int rows = stmt.executeUpdate();
            return rows > 0;
        }
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try {
            Connection connection = ConnectionProvider.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from product");
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setCategory(resultSet.getString("category"));
                product.setPrice(resultSet.getDouble("price"));
                product.setActive(resultSet.getString("active"));
                products.add(product);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Product getProductById(int id) {
        Product product = null;
        try {
            Connection con = ConnectionProvider.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM product WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setCategory(rs.getString("category"));
                product.setPrice(rs.getDouble("price"));
                product.setActive(rs.getString("active"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    public boolean updateProduct(int id, String name, String category, double price, String active) {
        boolean isUpdated = false;
        try {
            Connection con = ConnectionProvider.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE product SET name = ?, category = ?, price = ?, active = ? WHERE id = ?");
            ps.setString(1, name);
            ps.setString(2, category);
            ps.setDouble(3, price);
            ps.setString(4, active);
            ps.setInt(5, id);
            int rowCount = ps.executeUpdate();
            if (rowCount > 0) {
                isUpdated = true;
            }
            if ("No".equals(active)) {
                PreparedStatement ps2 = con.prepareStatement("DELETE FROM cart WHERE product_id = ? AND address IS NULL");
                ps2.setInt(1, id);
                ps2.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    public List<Product> getActiveProducts() {
        List<Product> products = new ArrayList<>();
        try {
            Connection con = ConnectionProvider.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM product WHERE active = 'Yes'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setCategory(rs.getString("category"));
                product.setPrice(rs.getDouble("price"));
                product.setActive(rs.getString("active"));
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> searchProducts(String search) {
        List<Product> products = new ArrayList<>();
        try {
            Connection con = ConnectionProvider.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM product WHERE (name LIKE ? OR category LIKE ?) AND active = 'Yes'");
            ps.setString(1, "%" + search + "%");
            ps.setString(2, "%" + search + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setCategory(rs.getString("category"));
                product.setPrice(rs.getDouble("price"));
                product.setActive(rs.getString("active"));
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

}
