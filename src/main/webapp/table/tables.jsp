<%@ page import="com.connection.ConnectionProvider" %>
<%@ page import="java.sql.*" %>

<%
    try {
        Connection con = ConnectionProvider.getConnection();
        Statement st = con.createStatement();
        String q1 = "create table users(name varchar(100), email varchar(100) primary key, mobileNumber integer, securityQuestion varchar(200), answer varchar(200), password varchar(100), address varchar(500), city varchar(100), state varchar(100), country varchar(100))";
        System.out.println(q1);
        st.executeUpdate(q1);
        System.out.println("Table created");
        con.close();
    } catch (Exception e) {
        System.out.println(e.getMessage());
        System.out.println("Exception caught");
    }
%>
