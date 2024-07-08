<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="adminHeader.jsp" %>
<%@ include file="footer.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/all-product-edit-style.css">
    <title>All Products & Edit Products</title>
    <style>
        .price-table {
            width: 100%;
            margin-top: 10px;
        }
        .price-table th, .price-table td {
            padding: 5px;
            text-align: left;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>All Products & Edit Products <i class="fas fa-edit"></i></h2>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>NAME</th>
            <th>CATEGORY</th>
            <th>PRICES</th>
            <th>ACTIVE</th>
            <th>EDIT</th>
            <th>ADD NEW PRICE</th>
            <th>DELETE</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>${product.id}</td>
                <td>${product.name}</td>
                <td>
                    <c:forEach var="category" items="${product.categories}">
                        ${category.name}<br/>
                    </c:forEach>
                </td>
                <td>
                    <table class="price-table">
                        <thead>
                        <tr>
                            <th>Price</th>
                            <th>Start Date</th>
                            <th>End Date</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="price" items="${product.prices}">
                            <tr>
                                <td>${price.price}</td>
                                <td>${price.startDate}</td>
                                <td>${price.endDate}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </td>
                <td>${product.active}</td>
                <td><a href="<c:url value='/editProduct?id=${product.id}'/>" class="btn-edit">Edit</a></td>
                <td><a href="<c:url value='/addNewPrice?id=${product.id}'/>" class="btn-add-price">Add New Price</a></td>
                <td>
                    <form action="<c:url value='/deleteProduct'/>" method="post">
                        <input type="hidden" name="productId" value="${product.id}">
                        <button type="submit" class="btn-delete">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
