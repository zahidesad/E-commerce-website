<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
<%@ include file="footer.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>My Addresses</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/myAddress.css">
</head>
<body>
<div class="container">
    <div class="content-header">My Addresses</div>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Address</th>
            <th>City</th>
            <th>State</th>
            <th>Country</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="address" items="${addresses}">
            <tr>
                <td>${address.id}</td>
                <td>${address.address}</td>
                <td>${address.city}</td>
                <td>${address.state}</td>
                <td>${address.country}</td>
                <td><a href="<c:url value='/editAddress?id=${address.id}'/>" class="btn btn-primary">Edit</a></td>
                <td>
                    <form action="<c:url value='/deleteAddress'/>" method="post">
                        <input type="hidden" name="addressId" value="${address.id}">
                        <button type="submit" class="btn btn-danger">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="add-address-container">
        <a href="<c:url value='/addAddress'/>" class="btn btn-success add-address-btn">Add New Address</a>
    </div>
</div>
</body>
</html>
