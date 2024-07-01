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
        <form action="<c:url value='/j_spring_security_check'/>" method="post">
            <label for="j_username">Email:</label>
            <input type="text" id="j_username" name="j_username" required>
            <label for="j_password">Password:</label>
            <input type="password" id="j_password" name="j_password" required>
            <button type="submit">Login</button>
        </form>

        <h2><a href="<c:url value='/register' />">Sign Up</a></h2>
        <h2><a href="<c:url value='/forgotPassword' />">Forgot Password?</a></h2>
    </div>
    <div class='whysignLogin'>
        <c:choose>
            <c:when test="${param.error != null}">
                <h1>Invalid username or password.</h1>
            </c:when>
            <c:when test="${param.logout != null}">
                <h1>You have been logged out.</h1>
            </c:when>
        </c:choose>
        <h2>Online Shopping</h2>
        <p>The Online Shopping System is the application that allows the users to shop online without going to the shops to buy them.</p>
    </div>
</div>

</body>
</html>
