<%@page import="com.connection.ConnectionProvider"%>
<%@page import="java.sql.*"%>
<%@include file="adminHeader.jsp"%>
<%@include file="../footer.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
<style>
h3
{
	color: yellow;
	text-align: center;
}
</style>
</head>
<body>
<div style="color: white; text-align: center; font-size: 30px;">All Products & Edit Products <i class='fab fa-elementor'></i></div>
<%
    String msg = request.getParameter("msg");
    if("done".equals(msg)){
%>
<h3 class="alert">Product Successfully Updated!</h3>
<%}%>

<%
    if("wrong".equals(msg)){
%>
<h3 class="alert">Something went wrong! Try again!</h3>
<%}%>

<table>
        <thead>
          <tr>
            <th scope="col">ID</th>
            <th scope="col">Name</th>
            <th scope="col">Category</th>
            <th scope="col"><i class="fa fa-inr"></i> Price</th>
            <th>Status</th>
            <th scope="col">Edit <i class='fas fa-pen-fancy'></i></th>
          </tr>
        </thead>
        <tbody>
        <%
            try{
                Connection com.connection = ConnectionProvider.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from product");
                while(resultSet.next()){

        %>
       
          <tr>
            <td><%=resultSet.getString(1) %></td>
            <td><%=resultSet.getString(2) %></td>
            <td><%=resultSet.getString(3) %></td>
            <td><i class="fa fa-inr"></i> <%=resultSet.getString(4) %></td>
            <td><%=resultSet.getString(5) %></td>
            <td><a href="editProduct.jsp?id=<%=resultSet.getString(1) %>">Edit <i class='fas fa-pen-fancy'></i></a></td>
          </tr>
        <%
            }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }%>
        </tbody>
      </table>
      <br>
      <br>
      <br>

</body>
</html>