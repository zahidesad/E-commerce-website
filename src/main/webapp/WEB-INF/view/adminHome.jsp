<%@include file="adminHeader.jsp"%>
<%@include file="footer.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>welcome</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin-home-style.css">
</head>
<body>
<div class="content">
    <h1>Welcome admin!</h1>
    <div class="quick-links">
        <div class="card">
            <h2>Add New Product</h2>
            <a href="addNewProduct">Go to Add Product</a>
        </div>
        <div class="card">
            <h2>View Orders</h2>
            <a href="admin/orders">Go to Orders</a>
        </div>
        <div class="card">
            <h2>Manage Products</h2>
            <a href="allProductEditProduct">Go to Manage Products</a>
        </div>
    </div>
</div>
</body>
</html>
