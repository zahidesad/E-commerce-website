<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
<%@ include file="footer.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
    <title>Search Results</title>
</head>
<body>
<div style="color: black; text-align: center; font-size: 30px;">Search Results <i class="fa fa-institution"></i></div>
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
            <td>
                <form action="<c:url value='/addToCart'/>" method="post">
                    <input type="hidden" name="productId" value="${product.id}">
                    <input type="hidden" name="quantity" value="1">
                    <button type="submit">Add to cart <i class='fas fa-cart-plus'></i></button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<c:if test="${not empty message}">
    <h1 style="color:white; text-align: center;">${message}</h1>
</c:if>
<br>
<br>
<br>
<div class="footer">
    <p>All right reserved by Zahid Esad</p>
</div>

</body>
</html>
