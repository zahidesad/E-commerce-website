<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
<%@ include file="footer.jsp" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="ISO-8859-1">
  <title>Order Details</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/orderDetails.css">
  <script src="https://js.stripe.com/v3/"></script>
</head>
<body>
<div class="container">
  <div class="content-header">Order Details</div>

  <!-- Address Selection -->
  <h3>Select Address</h3>
  <form id="payment-form" action="${pageContext.request.contextPath}/completeOrder" method="post">
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

    <!-- Credit Card Details -->
    <div id="card-element">
      <!-- A Stripe Element will be inserted here. -->
    </div>
    <div id="card-errors" role="alert"></div>

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
      <input type="hidden" name="totalAmount" value="${total}">
      <input type="hidden" id="stripeToken" name="stripeToken">
    </div>

    <!-- Complete Order Button -->
    <button type="submit" class="btn-complete-order">Complete the Order</button>
  </form>
</div>

<script>
  var stripe = Stripe('pk_test_51PfHyLFNLzhEqB0ltq8LI9pU4kEKB4ZqznLIqLoaOFt4puH1bysI4FsVmzLQ1DCWmqm6fpd11HB0HS3LCDFnBaI500hBfYzkJH');
  var elements = stripe.elements();

  var style = {
    base: {
      color: "#32325d",
      fontFamily: '"Helvetica Neue", Helvetica, sans-serif',
      fontSmoothing: "antialiased",
      fontSize: "16px",
      "::placeholder": {
        color: "#aab7c4"
      }
    },
    invalid: {
      color: "#fa755a",
      iconColor: "#fa755a"
    }
  };

  var card = elements.create("card", { style: style });
  card.mount("#card-element");

  var form = document.getElementById('payment-form');
  form.addEventListener('submit', function(event) {
    event.preventDefault();

    stripe.createToken(card).then(function(result) {
      if (result.error) {
        // Inform the user if there was an error.
        var errorElement = document.getElementById('card-errors');
        errorElement.textContent = result.error.message;
      } else {
        // Send the token to your server.
        var hiddenInput = document.getElementById('stripeToken');
        hiddenInput.value = result.token.id;

        form.submit();
      }
    });
  });
</script>

</body>
</html>
