<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page errorPage="error.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home-style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        .topnav {
            display: flex;
            justify-content: center;
            align-items: center;
            background-color: #333;
            margin: 0;
            padding: 0;
        }
        .topnav a {
            padding: 14px 16px;
            text-decoration: none;
            color: white;
        }
        .topnav h2 {
            margin: 0;
            padding: 14px 16px;
            color: white;
        }
        .search-container {
            display: flex;
            align-items: center;
            margin-left: auto;
        }
        .search-container input[type="text"] {
            padding: 6px;
            margin-right: 6px;
            font-size: 17px;
            border: none;
            border-radius: 4px;
        }
        .search-container button {
            padding: 6px 10px;
            background: #ddd;
            font-size: 17px;
            border: none;
            cursor: pointer;
            border-radius: 4px;
        }
        .search-container button:hover {
            background: #ccc;
        }
    </style>
</head>
<body>
<!--Header-->
<div class="topnav sticky">
    <c:choose>
        <c:when test="${not empty sessionScope.email}">
            <center><h2>Online shopping </h2></center>
            <h2><a href=""> ${sessionScope.email} <i class='fas fa-user-alt'></i></a></h2>
            <a href="<c:url value='/home'/>">Home <i class="fa fa-home"></i></a>
            <a href="<c:url value='/myCart'/>">My Cart <i class='fas fa-cart-arrow-down'></i></a>
            <a href="<c:url value='/myOrders'/>">My Orders <i class='fab fa-elementor'></i></a>
            <a href="<c:url value='/myAddress'/>">My Address <i class="fa fa-address-book"></i></a>
            <a href="<c:url value='/profile'/>">My Profile <i class='fa fa-user'></i></a>
            <a href="<c:url value='/logout'/>">Logout <i class='fas fa-share-square'></i></a>
            <div class="search-container">
                <form action="<c:url value='/search'/>" method="get">
                    <input type="text" placeholder="Search" name="query">
                    <button type="submit"><i class="fa fa-search"></i></button>
                </form>
            </div>
        </c:when>
        <c:otherwise>
            <center><h2>Online shopping </h2></center>
            <a href="<c:url value='/login'/>">Login <i class="fa fa-sign-in-alt"></i></a>
        </c:otherwise>
    </c:choose>
</div>
<!--table-->
</body>
</html>
