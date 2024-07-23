<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
<%@ include file="footer.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Product Details</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/productDetails.css">
</head>
<body>
<div class="container">
    <c:if test="${not empty msg}">
        <div class="alert alert-warning">
            <strong>${product.name}</strong> is out of stock.
            <p>Price: ${product.currentPriceValue}</p>
        </div>
    </c:if>
</div>
</body>
</html>
