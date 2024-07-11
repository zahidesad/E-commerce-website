<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header.jsp" %>
<%@ include file="footer.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="<c:url value='/css/edit-address-style.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <title>Edit Address</title>
</head>
<body>
<div class="container">
    <h2>Edit Address <i class="fas fa-edit"></i></h2>
    <form action="<c:url value='/updateAddress'/>" method="post">
        <input type="hidden" name="id" value="${address.id}">
        <div class="form-group">
            <label for="address">Address:</label>
            <input type="text" id="address" name="address" value="${address.address}" required>
        </div>
        <div class="form-group">
            <label for="city">City:</label>
            <input type="text" id="city" name="city" value="${address.city}" required>
        </div>
        <div class="form-group">
            <label for="state">State:</label>
            <input type="text" id="state" name="state" value="${address.state}" required>
        </div>
        <div class="form-group">
            <label for="country">Country:</label>
            <input type="text" id="country" name="country" value="${address.country}" required>
        </div>
        <button class="btn" type="submit">Save <i class='far fa-arrow-alt-circle-right'></i></button>
    </form>
</div>
</body>
</html>
