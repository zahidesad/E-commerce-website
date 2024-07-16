<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="adminHeader.jsp" %>
<%@ include file="footer.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Order Details</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminOrderDetails.css">
</head>
<body>
<div class="container">
    <div class="content-header">Order Details</div>

    <h3>Order Information</h3>
    <p>Order ID: ${order.id}</p>
    <p>Order Date: ${order.orderDate}</p>
    <p>Delivery Date: ${order.deliveryDate}</p>
    <p>Status:
    <form action="${pageContext.request.contextPath}/admin/updateOrderStatus" method="post">
        <input type="hidden" name="orderId" value="${order.id}">
        <select name="status">
            <option value="Pending" ${order.status == 'Pending' ? 'selected' : ''}>Pending</option>
            <option value="Approved" ${order.status == 'Approved' ? 'selected' : ''}>Approved</option>
            <option value="Shipped" ${order.status == 'Shipped' ? 'selected' : ''}>Shipped</option>
            <option value="Delivered" ${order.status == 'Delivered' ? 'selected' : ''}>Delivered</option>
            <option value="Cancelled" ${order.status == 'Cancelled' ? 'selected' : ''}>Cancelled</option>
        </select>
        <button type="submit">Update</button>
    </form>
    </p>
    <p>Payment Method: ${order.paymentMethod}</p>
    <p>Transaction ID: ${order.transactionId}</p>

    <h3>Address</h3>
    <p>${order.address.address}, ${order.address.city}, ${order.address.state}, ${order.address.country}</p>

    <h3>Order Items</h3>
    <table>
        <thead>
        <tr>
            <th>Product Name</th>
            <th>Quantity</th>
            <th>Price</th>
            <th>Total</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${order.orderItems}">
            <tr>
                <td>${item.product.name}</td>
                <td>${item.quantity}</td>
                <td>${item.price}</td>
                <td>${item.total}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <h3>Total Price: ${order.total}</h3>
</div>
</body>
</html>
