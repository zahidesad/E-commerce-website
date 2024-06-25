<%@page errorPage="../error.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="../../css/home-style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>
<!--Header-->
<br>
<div class="topnav sticky">
    <% String email = session.getAttribute("email").toString(); %>
    <center><h2>Online shopping</h2></center>
    <a href="addNewProduct.jsp">Add New Product <i class="fas fa-plus"></i></a>
    <a href="allProductEditProduct.jsp">All Products & Edit Products <i class="fas fa-edit"></i></a>
    <a href="">Messages Received <i class="fas fa-comments"></i></a>
    <a href="">Orders Received <i class="fas fa-box"></i></a>
    <a href="">Cancel Orders <i class="fas fa-times"></i></a>
    <a href="">Delivered Orders <i class="fas fa-truck"></i></a>
    <a href="">Logout <i class="fas fa-sign-out-alt"></i></a>
</div>
<br>
<!--table-->
</body>
</html>