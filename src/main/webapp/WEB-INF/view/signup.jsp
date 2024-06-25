<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
  <link rel="stylesheet" href="css/signup-style.css">
  <title>Signup</title>
</head>
<body>
<div id='container'>
  <div class='signup'>
    <form action="<c:url value='/register' />" method="post">
      <input type="text" name="name" placeholder="Enter Name" required>
      <input type="email" name="email" placeholder="Enter Email" required>
      <input type="number" name="mobileNumber" placeholder="Enter Mobil Number" required>
      <select name="securityQuestion" required>
        <option value="What was your first car?">What was your first car?</option>
        <option value="What is the name of your first pet?">What is the name of your first pet?</option>
        <option value="What elementary school did you attend?">What elementary school did you attend?</option>
        <option value="What is the name of the town where you were born?">What is the name of the town where you were born?</option>
      </select>
      <input type="text" name="answer" placeholder="Enter Answer" required>
      <input type="password" name="password" placeholder="Enter Password" required>
      <input type="submit" value="Sign up">
    </form>
    <h2><a href="<c:url value="/login"/>">Login</a></h2>
  </div>
  <div class='whysign'>
    <c:choose>
      <c:when test="${msg == 'valid'}">
        <h1>Successfully Registered !</h1>
      </c:when>
      <c:when test="${msg == 'invalid'}">
        <h1>Something Went Wrong! Try Again !</h1>
      </c:when>
    </c:choose>
    <h2>Online Shopping</h2>
    <p>The Online Shopping System is the application that allows the users to shop online without going to the shops to buy them.</p>
  </div>
</div>
</body>
</html>
