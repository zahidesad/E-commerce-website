<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="adminHeader.jsp" %>
<%@ include file="footer.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Admin Orders</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminOrders.css">
</head>
<body>
<div class="container">
    <div class="content-header">Manage Orders</div>

    <c:if test="${orders != null && !orders.isEmpty()}">
        <table class="order-table">
            <thead>
            <tr>
                <th>Order ID</th>
                <th>Customer Name</th>
                <th>Customer Surname</th>
                <th>Order Date</th>
                <th>Total</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${orders}">
                <tr>
                    <td>${order.id}</td>
                    <td>${order.user.name}</td>
                    <td>${order.user.surname}</td>
                    <td>${order.orderDate}</td>
                    <td>${order.total}</td>
                    <td>${order.status}</td>
                    <td><a href="${pageContext.request.contextPath}/admin/orderDetails?id=${order.id}">View Details</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${orders == null || orders.isEmpty()}">
        <p>No orders found.</p>
    </c:if>
</div>
</body>
</html>
