<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
<%@ include file="footer.jsp" %>
<!DOCTYPE html>
<html>
<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-1.10.2.js"></script>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-3">
            <div class="left-side-category">
                <h3 class="category-heading">Categories</h3>
                <ul class="category-list">
                    <c:forEach var="category" items="${parentCategories}">
                        <li class="category-item">
                            <a href="${pageContext.request.contextPath}/category/categoryProducts?categoryId=${category.id}">
                                    ${category.name} <span>(${categoryProductCounts[category.id]})</span>
                            </a>
                            <ul class="sub-category-list">
                                <c:forEach var="subCategory" items="${category.childCategories}">
                                    <li class="sub-category-item">
                                        <a href="${pageContext.request.contextPath}/category/categoryProducts?categoryId=${subCategory.id}">
                                                ${subCategory.name} <span>(${subCategory.products.size()})</span>
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <div class="col-md-9">
            <div class="product-list">
                <div class="product-list-header">
                    <h3>Products</h3>
                    <div class="sort-products">
                        <span>Sort By:</span>
                        <select>
                            <option value="popularity">Popularity</option>
                            <option value="price">Price</option>
                            <option value="rating">Rating</option>
                        </select>
                    </div>
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
                                <a href="${pageContext.request.contextPath}/productDetails?id=${product.id}" class="btn btn-primary">See Details</a>
                                <form action="${pageContext.request.contextPath}/addToCart" method="post">
                                    <input type="hidden" name="productId" value="${product.id}">
                                    <input type="hidden" name="quantity" value="1">
                                    <button type="submit" class="btn btn-success">Add To Cart</button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <div class="pagination">
                    <ul>
                        <li><a href="#">�</a></li>
                        <li><a href="#">1</a></li>
                        <li><a href="#">2</a></li>
                        <li><a href="#">3</a></li>
                        <li><a href="#">�</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
