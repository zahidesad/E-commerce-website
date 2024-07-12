<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
<%@ include file="footer.jsp" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="ISO-8859-1">
  <title>Order Details</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/orderDetails.css">
</head>
<body>
<div class="container">
  <div class="content-header">Order Details</div>

  <!-- Address Selection -->
  <h3>Select Address</h3>
  <form action="${pageContext.request.contextPath}/completeOrder" method="post">
    <c:forEach var="address" items="${addresses}">
      <div class="address-item">
        <input type="radio" id="address${address.id}" name="addressId" value="${address.id}" required>
        <label for="address${address.id}">
            ${address.address}, ${address.city}, ${address.state}, ${address.country}
        </label>
      </div>
    </c:forEach>

    <!-- Payment Method Selection -->
    <h3>Select Payment Method</h3>
    <div class="payment-methods">
      <input type="radio" id="creditCard" name="paymentMethod" value="Credit Card" required>
      <label for="creditCard">Credit Card</label>

      <input type="radio" id="paypal" name="paymentMethod" value="PayPal" required>
      <label for="paypal">PayPal</label>

      <input type="radio" id="cod" name="paymentMethod" value="Cash on Delivery" required>
      <label for="cod">Cash on Delivery</label>
    </div>

    <!-- Order Summary -->
    <h3>Order Summary</h3>
    <table class="order-summary">
      <thead>
      <tr>
        <th>Product</th>
        <th>Quantity</th>
        <th>Price</th>
        <th>Total</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="item" items="${cartItems}">
        <tr>
          <td>${item.product.name}</td>
          <td>${item.quantity}</td>
          <td>${item.price}</td>
          <td>${item.total}</td>
        </tr>
      </c:forEach>
      </tbody>
    </table>

    <!-- Total Price -->
    <div class="total-container">
      <h3>Total: <span class="total-price">${total}</span></h3>
    </div>

    <!-- Complete Order Button -->
    <button type="submit" class="btn-complete-order">Complete the Order</button>
  </form>
</div>
</body>
</html>
