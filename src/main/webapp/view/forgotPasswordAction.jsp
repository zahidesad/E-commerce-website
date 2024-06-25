<%@ page import="com.connection.ConnectionProvider" %>
<%@ page import="java.sql.*" %>

<%
    String email = request.getParameter("email");
    String mobileNumber = request.getParameter("mobileNumber");
    String securityQuestion = request.getParameter("securityQuestion");
    String answer = request.getParameter("answer");
    String newPassword = request.getParameter("newPassword");

    boolean check = false;

    try {
        Connection com.connection = ConnectionProvider.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("Select *from users where email = '" + email + "' and mobileNumber = '" + mobileNumber + "'and securityQuestion = '" + securityQuestion + "' and answer = '" + answer + "'");

        while (resultSet.next()) {
            check = true;
            statement.executeUpdate("update users set password ='"+newPassword+"' where email = '" + email + "'");
            response.sendRedirect("forgotPassword.jsp?msg=done");
        }

        if (!check){
            response.sendRedirect("forgotPassword.jsp?msg=invalid");
        }


    }catch (Exception e) {
        System.out.println(e.getMessage());
    }

%>