<%@page import="com.connection.ConnectionProvider"%>
<%@page import="java.sql.*"%>

<%
    String email = session.getAttribute("email").toString();
    String address = request.getParameter("address");
    String city = request.getParameter("city");
    String state = request.getParameter("state");
    String country = request.getParameter("country");
    String mobileNumber = request.getParameter("mobileNumber");
    String paymentMethod = request.getParameter("paymentMethod");
    String transactionID = "";
    transactionID = request.getParameter("transactionID");
    String status = request.getParameter("bill");

    try {
        Connection connection = ConnectionProvider.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("update users set address=?,city=?,state=?,country,mobileNumber=? where email=?");
        preparedStatement.setString(1, address);
        preparedStatement.setString(2, city);
        preparedStatement.setString(3, state);
        preparedStatement.setString(4, country);
        preparedStatement.setString(5, mobileNumber);
        preparedStatement.setString(6, email);

        preparedStatement.executeUpdate();

        PreparedStatement ps = connection.prepareStatement("update cart set address=?,city=?,state=?,country,mobileNumber=?, orderDate= now(), deliveryDate=DATE_ADD(orderDate, INTERVAL 7 DAY), paymentMethod=?, transactionID =?, status=?, where email=? and address is NULL");
        ps.setString(1, address);
        ps.setString(2, city);
        ps.setString(3, state);
        ps.setString(4, country);
        ps.setString(5, mobileNumber);
        ps.setString(6, paymentMethod);
        ps.setString(7, transactionID);
        ps.setString(8, status);
        ps.setString(9, email);

        ps.executeUpdate();
        response.sendRedirect("bill.jsp");


    }catch (Exception e){
        System.out.println(e.getMessage());
    }
%>