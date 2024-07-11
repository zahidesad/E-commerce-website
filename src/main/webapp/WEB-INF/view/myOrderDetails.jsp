<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
<%@ include file="footer.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Order Details</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/orderDetails.css">
</head>
<body>
<div class="container">
    <div class="content-header">Order Details</div>
    <div class="content">
        <h3>Your Address:</h3>
        <p>${address.address}</p>
        <p>${address.city}, ${address.state}, ${address.country}</p>

        <h3>Your Cart Items:</h3>
        <table>
            <thead>
            <tr>
                <th>Product Name</th>
                <th>Quantity</th>
                <th>Price</th>
                <th>Sub Total</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="cartItem" items="${cart.cartItems}">
                <tr>
                    <td>${cartItem.product.name}</td>
                    <td>${cartItem.quantity}</td>
                    <td>${cartItem.price}</td>
                    <td>${cartItem.total}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <h3>Total: ${total}</h3>
        <form action="${pageContext.request.contextPath}/completeOrder" method="post">
            <button type="submit" class="btn btn-primary">Complete the Order</button>
        </form>
    </div>
</div>
</body>
</html>
