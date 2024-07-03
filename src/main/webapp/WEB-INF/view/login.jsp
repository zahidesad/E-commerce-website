<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signup-style.css">
    <title>Login</title>
</head>
<body>
<div id='container'>
    <div class='signup'>
        <form action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
            <label for="j_username">Email:</label>
            <input type="email" id="j_username" name="j_username" placeholder="Enter Email" required>
            <label for="j_password">Password:</label>
            <input type="password" id="j_password" name="j_password" placeholder="Enter Password" required>
            <button type="submit">Login</button>
        </form>
        <div class="links">
            <h2><a href="<c:url value='/register' />">Sign Up</a></h2>
            <h2><a href="<c:url value='/forgotPassword' />">Forgot Password?</a></h2>
        </div>
    </div>
    <div class='whysignLogin'>
        <c:choose>
            <c:when test="${msg == 'invalid'}">
                <h1>Something Went Wrong! Try Again!</h1>
            </c:when>
            <c:when test="${msg == 'notexist'}">
                <h1>Incorrect Username or Password</h1>
            </c:when>
        </c:choose>
        <h2>Online Shopping</h2>
        <p>The Online Shopping System allows users to shop online without visiting the stores.</p>
    </div>
</div>
</body>
</html>
