<%@page import="com.connection.ConnectionProvider"%>
<%@page import="java.sql.*"%>

<%
    String id = request.getParameter("id");
    String name = request.getParameter("name");
    String category = request.getParameter("category");
    String price = request.getParameter("price");
    String active = request.getParameter("active");

    try {
        Connection connection = ConnectionProvider.getConnection();
        PreparedStatement statement = connection.prepareStatement("insert into product values (?,?,?,?,?)");
        statement.setString(1, id);
        statement.setString(2, name);
        statement.setString(3, category);
        statement.setString(4, price);
        statement.setString(5, active);
        statement.executeUpdate();
        response.sendRedirect("addNewProduct.jsp?msg=done");

    }catch (Exception e) {
        response.sendRedirect("addNewProduct.jsp?msg=wrong");
    }

%>