<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="css/signup-style.css">
    <title>Login</title>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
</head>
<body>
<div id='container'>
    <div class='signup'>
        <form action="<c:url value='/login' />" method="post">
            <input type="email" name="email" placeholder="Enter Email" required>
            <input type="password" name="password" placeholder="Enter Password" required>
            <input type="submit" value="Login">
        </form>

        <h2><a href="<c:url value='/register' />">Sign Up</a></h2>
        <h2><a href="<c:url value='/forgotPassword' />">Forgot Password?</a></h2>
    </div>
    <div class='whysignLogin'>
        <c:choose>
            <c:when test="${msg == 'invalid'}">
                <h1>Something Went Wrong! Try Again !</h1>
            </c:when>
            <c:when test="${msg == 'notexist'}">
                <h1>Incorrect Username or Password</h1>
            </c:when>
        </c:choose>
        <h2>Online Shopping</h2>
        <p>The Online Shopping System is the application that allows the users to shop online without going to the shops to buy them.</p>
    </div>
</div>

</body>
</html>
