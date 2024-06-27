<%@ page import="com.connection.ConnectionProvider" %>
<%@ page import="java.sql.*" %>

<%
    String email = session.getAttribute("email").toString();
    String id = request.getParameter("id");
    String incdec = request.getParameter("quantity");
    int price = 0;
    int total = 0;
    int quantity = 0;
    int final_total = 0;

    try {
        Connection connection = ConnectionProvider.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select from cart where email='"+email+"' and product_id = '"+id+"'and address is NULL");
        while (resultSet.next()) {
            price = resultSet.getInt(4);
            total = resultSet.getInt(5);
            quantity = resultSet.getInt(3);
        }
        if (quantity == 1 && incdec.equals("dec")) {
            response.sendRedirect("myCart.jsp?msg=notPossible");
        } else if (quantity != 1 && incdec.equals("dec")) {
            total -=price;
            quantity--;
            statement.executeUpdate("update cart set total= '"+total+"', quantity = '"+quantity+"' where email = '"+email+"' and product_id = '"+id+"'and address is NULL");
            response.sendRedirect("myCart.jsp?msg=dec");
        }else {
            total +=price;
            quantity++;
            statement.executeUpdate("update cart set total= '"+total+"', quantity = '"+quantity+"' where email = '"+email+"' and product_id = '"+id+"'and address is NULL");
            response.sendRedirect("myCart.jsp?msg=inc");
        }

    }catch (Exception e) {
        System.out.println(e.getMessage());

    }

%>