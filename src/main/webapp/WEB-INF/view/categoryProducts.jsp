<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
<%@ include file="footer.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Category Products</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-9">
            <div class="product-list">
                <div class="product-list-header">
                    <h3>Products</h3>
                </div>
                <div class="row">
                    <c:forEach var="product" items="${products}">
                        <div class="col-md-4">
                            <div class="product-item">
                                <img src="${pageContext.request.contextPath}/images/dummyimg.png" alt="${product.name}">
                                <h4>${product.name}</h4>
                                <p>Price:
                                    <c:choose>
                                        <c:when test="${product.currentPriceValue != null}">
                                            ${product.currentPriceValue}
                                        </c:when>
                                        <c:otherwise>
                                            Not Available
                                        </c:otherwise>
                                    </c:choose>
                                </p>
                                <a href="${pageContext.request.contextPath}/category/productDetails?id=${product.id}" class="btn btn-primary">See Details</a>
                                <form action="${pageContext.request.contextPath}/addToCart" method="post">
                                    <input type="hidden" name="productId" value="${product.id}">
                                    <input type="hidden" name="quantity" value="1">
                                    <button type="submit" class="btn btn-success">Add To Cart</button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
