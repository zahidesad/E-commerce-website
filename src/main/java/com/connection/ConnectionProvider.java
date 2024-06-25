package com.connection;

import java.sql.*;

public class ConnectionProvider {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce", "root", "1234");
            return con;
        }catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Connection failed");
            return null;
        }
    }
}