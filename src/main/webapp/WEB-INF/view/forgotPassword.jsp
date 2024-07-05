<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="css/signup-style.css">
    <title>Forgot Password</title>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
</head>
<body>
<div id='container'>
    <div class='signup'>
        <form action="<c:url value='/forgotPassword' />" method="post">
            <input type="email" name="email" placeholder="Enter Email" required>
            <input type="number" name="mobileNumber" placeholder="Enter Mobile Number" required>
            <select name="securityQuestion">
                <option value="c">What was your first car?</option>
                <option value="What is the name of your first pet?">What is the name of your first pet?</option>
                <option value="What elementary school did you attend?">What elementary school did you attend?</option>
                <option value="What is the name of the town where you were born?">What is the name of the town where you were born?</option>
            </select>
            <input type="text" name="answer" placeholder="Enter Answer" required>
            <input type="password" name="newPassword" placeholder="Enter your new password to set" required>
            <input type="submit" value="Save">
        </form>
        <div class="links">
            <h2><a href="<c:url value='/login' />">Login</a></h2>
        </div>
    </div>
    <div class='whyforgotPassword'>
        <c:choose>
            <c:when test="${param.msg == 'done'}">
                <h1>Password Changed Successfully!</h1>
            </c:when>
            <c:when test="${param.msg == 'invalid'}">
                <h1>Something Went Wrong! Try Again!</h1>
            </c:when>
            <c:when test="${param.msg == 'notexist'}">
                <h1>User does not exist! Please sign up!</h1>
            </c:when>
        </c:choose>
        <h2>Online Shopping</h2>
        <p>The Online Shopping System allows users to shop online without visiting the stores.</p>
    </div>
</div>
</body>
</html>
