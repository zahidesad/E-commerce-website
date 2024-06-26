<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="adminHeader.jsp" %>
<%@ include file="footer.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>All Products & Edit Products</title>
    <style>
        h3 {
            color: yellow;
            text-align: center;
        }
    </style>
</head>
<body>
<div style="color: white; text-align: center; font-size: 30px;">All Products & Edit Products <i class='fab fa-elementor'></i></div>

<c:if test="${not empty message}">
    <h3 class="alert">${message}</h3>
</c:if>

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
    <c:forEach var="product" items="${products}">
        <tr>
            <td>${product.id}</td>
            <td>${product.name}</td>
            <td>${product.category}</td>
            <td><i class="fa fa-inr"></i> ${product.price}</td>
            <td>${product.active}</td>
            <td><a href="editProduct?id=${product.id}">Edit <i class='fas fa-pen-fancy'></i></a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<br><br><br>
</body>
</html>
