<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
<%@ include file="footer.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>My Orders</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/myOrder.css">
</head>
<body>
<div class="container">
    <div class="content-header">My Orders</div>

    <c:if test="${orders != null && !orders.isEmpty()}">
        <table class="order-table">
            <thead>
            <tr>
                <th>Order ID</th>
                <th>Order Date</th>
                <th>Delivery Date</th>
                <th>Status</th>
                <th>Total</th>
                <th>Details</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${orders}">
                <tr>
                    <td>${order.id}</td>
                    <td>${order.orderDate}</td>
                    <td>${order.deliveryDate}</td>
                    <td>${order.status}</td>
                    <td>${order.total}</td>
                    <td><a href="">View Details</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${orders == null || orders.isEmpty()}">
        <p>You have no orders.</p>
    </c:if>
</div>
</body>
</html>
