<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
<%@ include file="footer.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>My Cart</title>
    <style>
        h3 {
            color: yellow;
            text-align: center;
        }
    </style>
</head>
<body>
<div style="color: white; text-align: center; font-size: 30px;">My Cart <i class='fas fa-cart-arrow-down'></i></div>

<c:if test="${param.msg == 'notPossible'}">
    <h3 class="alert">There is only one Quantity! So click on remove!</h3>
</c:if>
<c:if test="${param.msg == 'inc'}">
    <h3 class="alert">Quantity Increased Successfully!</h3>
</c:if>
<c:if test="${param.msg == 'dec'}">
    <h3 class="alert">Quantity Decreased Successfully!</h3>
</c:if>
<c:if test="${param.msg == 'removed'}">
    <h3 class="alert">Product Successfully Removed!</h3>
</c:if>

<table>
    <thead>
    <c:choose>
        <c:when test="${total > 0}">
            <tr>
                <th scope="col" style="background-color: yellow;">Total: <i class="fa fa-inr"></i> ${total} </th>
                <th scope="col"><a href="addressPaymentForOrder.jsp">Proceed to order</a></th>
            </tr>
        </c:when>
        <c:otherwise>
            <tr>
                <th scope="col" style="background-color: yellow;">Total: <i class="fa fa-inr"></i> ${total} </th>
            </tr>
        </c:otherwise>
    </c:choose>
    </thead>
    <thead>
    <tr>
        <th scope="col">S.No</th>
        <th scope="col">Product Name</th>
        <th scope="col">Category</th>
        <th scope="col"><i class="fa fa-inr"></i> price</th>
        <th scope="col">Quantity</th>
        <th scope="col">Sub Total</th>
        <th scope="col">Remove <i class='fas fa-trash-alt'></i></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="product" items="${products}" varStatus="status">
        <tr>
            <td>${status.index + 1}</td>
            <td>${product.name}</td>
            <td>${product.category}</td>
            <td><i class="fa fa-inr"></i> ${product.price}</td>
            <td>
                <a href="incDecQuantity?id=${product.id}&quantity=dec"><i class='fas fa-minus-circle'></i></a>
                    ${product.quantity}
                <a href="incDecQuantity?id=${product.id}&quantity=inc"><i class='fas fa-plus-circle'></i></a>
            </td>
            <td><i class="fa fa-inr"></i> ${product.total}</td>
            <td><a href="removeFromCart?id=${product.id}">Remove <i class='fas fa-trash-alt'></i></a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<br>
<br>
<br>

</body>
</html>
