<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
<%@ include file="footer.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Order Confirmation</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/orderConfirmation.css">
</head>
<body>
<div class="container">
    <div class="content-header">Order Confirmation</div>
    <p>Your order has been successfully placed. Thank you for shopping with us!</p>
    <a href="${pageContext.request.contextPath}/home">Go to Home</a>
</div>
</body>
</html>
