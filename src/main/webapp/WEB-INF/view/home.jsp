<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
<%@ include file="footer.jsp" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Home</title>
    <style>
        h3 {
            color: yellow;
            text-align: center;
        }
        .alert {
            color: yellow;
            text-align: center;
        }
    </style>
</head>
<body>
<div style="color: white; text-align: center; font-size: 30px;">Home <i class="fa fa-institution"></i></div>
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
        <th scope="col">Add to cart <i class='fas fa-cart-plus'></i></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="product" items="${products}">
        <tr>
            <td>${product.id}</td>
            <td>${product.name}</td>
            <td>${product.category}</td>
            <td><i class="fa fa-inr"></i> ${product.price}</td>
            <td><a href="<c:url value='/addToCart'/>?id=${product.id}">Add to cart <i class='fas fa-cart-plus'></i></a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<br>
<br>
<br>
</body>
</html>
