<%@ page import="com.connection.ConnectionProvider" %>
<%@ page import="java.sql.*" %>

<%
    String email = session.getAttribute("email").toString();
    String product_id = request.getParameter("id");
    int quantity = 1;
    int product_price = 0;
    int product_total= 0;
    int cart_total = 0;

    boolean isExist = false;

    try {
        Connection connection = ConnectionProvider.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet  = statement.executeQuery("select * from product where id = '"+product_id+"'");
        while (resultSet.next()) {
            product_price = resultSet.getInt(4);
            product_total = product_price;
        }
        ResultSet rs = statement.executeQuery("select * from cart where product_id = '"+product_id+"'and email='"+email+"' and address is NULL");
        while (rs.next()) {
            cart_total = rs.getInt(5);
            cart_total += product_total;
            quantity = rs.getInt(3);
            quantity++;
            isExist = true;
        }
        if (isExist){
            statement.executeUpdate("update cart set total = '"+ cart_total+"', quantity = '"+ quantity+"' where product_id = '"+product_id+"' and email='"+email+"' and address is NULL");
            response.sendRedirect("home.jsp?msg=exist");
        }else {
            PreparedStatement ps = connection.prepareStatement("insert into cart(email,product_id,quantity,price,total) values(?,?,?,?,?)");
            ps.setString(1, email);
            ps.setString(2, product_id);
            ps.setInt(3, quantity);
            ps.setInt(4, product_price);
            ps.setInt(5, product_total);
            ps.executeUpdate();
            response.sendRedirect("home.jsp?msg=added");
        }

    }catch (Exception e) {
        System.out.println(e.getMessage());
        response.sendRedirect("home.jsp?msg=invalid");
    }
%>