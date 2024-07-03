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
            padding: 14px;
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
    </style>
</head>
<body>
<!--Header-->
<div class="topnav sticky">
    <% String email = session.getAttribute("email").toString(); %>
    <h2>Online shopping</h2>
    <a href="${pageContext.request.contextPath}/addNewProduct">Add New Product <i class="fas fa-plus"></i></a>
    <a href="${pageContext.request.contextPath}/allProductEditProduct">All Products & Edit Products <i class="fas fa-edit"></i></a>
    <a href="">Messages Received <i class="fas fa-comments"></i></a>
    <a href="">Orders Received <i class="fas fa-box"></i></a>
    <a href="">Cancel Orders <i class="fas fa-times"></i></a>
    <a href="">Delivered Orders <i class="fas fa-truck"></i></a>
    <a href="${pageContext.request.contextPath}/logout">Logout <i class="fas fa-sign-out-alt"></i></a>
</div>
<!--table-->
</body>
</html>
