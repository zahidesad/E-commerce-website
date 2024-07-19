<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/profile.css">
</head>
<body>
<div class="container">
    <h2>Profile</h2>
    <c:if test="${not empty msg}">
        <div class="msg">${msg}</div>
    </c:if>
    <form:form method="post" modelAttribute="user">
        <form:label path="email">Email:</form:label>
        <form:input path="email"/>
        <form:label path="mobileNumber">Mobile Number:</form:label>
        <form:input path="mobileNumber"/>
        <input type="submit" value="Update"/>
    </form:form>
</div>
</body>
</html>
