<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
<%@ include file="footer.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>My Address</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/addAddress.css">
</head>
<body>
<div class="container">
    <div class="content-header">My Address</div>
    <c:if test="${param.error != null}">
        <div class="alert">${param.error}</div>
    </c:if>
    <form class="address-form" action="${pageContext.request.contextPath}/saveAddress" method="post">
        <label for="address">Address</label>
        <input type="text" id="address" name="address" required>

        <label for="city">City</label>
        <input type="text" id="city" name="city" required>

        <label for="state">State</label>
        <input type="text" id="state" name="state" required>

        <label for="country">Country</label>
        <input type="text" id="country" name="country" required>

        <button type="submit" class="btn-primary">Save Address</button>
    </form>
</div>
</body>
</html>
