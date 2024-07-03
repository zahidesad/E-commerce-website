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
<br>
<div class="topnav sticky">
    <%
        String email = (String) session.getAttribute("email").toString();
        if (email != null) {
    %>
    <center><h2>Online shopping </h2></center>
    <h2><a href=""> <% out.println(email); %> <i class='fas fa-user-alt'></i></a></h2>
    <a href="<c:url value='/home'/>">Home <i class="fa fa-home"></i></a>
    <a href="<c:url value='/myCart'/>">My Cart <i class='fas fa-cart-arrow-down'></i></a>
    <a href="">My Orders <i class='fab fa-elementor'></i></a>
    <a href="">Change Details <i class="fa fa-edit"></i></a>
    <a href="">Message Us <i class='fas fa-comment-alt'></i></a>
    <a href="">About <i class="fa fa-address-book"></i></a>
    <a href="<c:url value='/logout'/>">Logout <i class='fas fa-share-square'></i></a>
    <div class="search-container">
        <form action="<c:url value='/search'/>" method="get">
            <input type="text" placeholder="Search" name="query">
            <button type="submit"><i class="fa fa-search"></i></button>
        </form>
    </div>
    <%
    } else {
    %>
    <center><h2>Online shopping </h2></center>
    <a href="<c:url value='/login'/>">Login <i class="fa fa-sign-in-alt"></i></a>
    <%
        }
    %>
</div>
<br>
<!--table-->
</body>
</html>