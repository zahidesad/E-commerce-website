<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>
<%@ include file="footer.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>My Cart</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/myCart.css">
</head>
<body>
<div class="container">
    <div class="content-header">My Cart <i class='fas fa-cart-arrow-down'></i></div>

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

    <div class="content">
        <table>
            <thead>
            <tr>
                <th scope="col">S.No</th>
                <th scope="col">Product Name</th>
                <th scope="col">Quantity</th>
                <th scope="col"><i class="fa fa-inr"></i> Price</th>
                <th scope="col">Sub Total</th>
                <th scope="col">Remove <i class='fas fa-trash-alt'></i></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="cartItem" items="${cart.cartItems}" varStatus="status">
                <tr>
                    <td>${status.index + 1}</td>
                    <td>${cartItem.product.name}</td>
                    <td>
                        <form action="${pageContext.request.contextPath}/incDecQuantity" method="get" style="display:inline;">
                            <input type="hidden" name="id" value="${cartItem.id}">
                            <input type="hidden" name="quantity" value="dec">
                            <button class="quantity-btn" type="submit"><i class='fas fa-minus-circle'></i></button>
                        </form>
                            ${cartItem.quantity}
                        <form action="${pageContext.request.contextPath}/incDecQuantity" method="get" style="display:inline;">
                            <input type="hidden" name="id" value="${cartItem.id}">
                            <input type="hidden" name="quantity" value="inc">
                            <button class="quantity-btn" type="submit"><i class='fas fa-plus-circle'></i></button>
                        </form>
                    </td>
                    <td><i class="fa fa-inr"></i> ${cartItem.price}</td>
                    <td><i class="fa fa-inr"></i> ${cartItem.total}</td>
                    <td>
                        <form action="${pageContext.request.contextPath}/removeFromCart" method="get">
                            <input type="hidden" name="id" value="${cartItem.id}">
                            <button type="submit">Remove <i class='fas fa-trash-alt'></i></button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="total-container">
            <c:choose>
                <c:when test="${total > 0}">
                    <div class="total-header">Total: <i class="fa fa-inr"></i> ${total} </div>
                    <a class="proceed-link" href="addressPaymentForOrder.jsp">Proceed to order</a>
                </c:when>
                <c:otherwise>
                    <div class="total-header">Total: <i class="fa fa-inr"></i> ${total} </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>
