<%@ page import="com.connection.ConnectionProvider" %>
<%@ page import="java.sql.*" %>

<%
    String email = request.getParameter("email");
    String password = request.getParameter("password");

    if ("admin@gmail.com".equals(email) && "admin".equals(password)) {

        session.setAttribute("email", email);
        response.sendRedirect("admin/adminHome.jsp");
    }else {
        boolean isExist = false;

        try {
            Connection con = ConnectionProvider.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select *from users where email='"+email+"' and password='"+password+"'");
            while (rs.next()) {
                isExist = true;
                session.setAttribute("email", email);
                response.sendRedirect("home.jsp");
            }
            if (!isExist){
                response.sendRedirect("login.jsp?msg=notexist");
            }

        }catch (Exception e) {
            System.out.println(e.getMessage());
            response.sendRedirect("login.jsp?msg=invalid");
        }
    }
%>
