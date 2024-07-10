<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
<%@ include file="footer.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Product Details</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-6">
            <img src="${pageContext.request.contextPath}/images/Iphone14Pro.jpg" alt="${product.name}">
        </div>
        <div class="col-md-6">
            <h2>${product.name}</h2>
            <p>Price: <c:out value="${product.prices[0].price}" /></p>
            <p>Description: ${product.description}</p>
            <form action="${pageContext.request.contextPath}/addToCart" method="post">
                <input type="hidden" name="productId" value="${product.id}">
                <input type="hidden" name="quantity" value="1">
                <button type="submit" class="btn btn-success">Add To Cart</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
