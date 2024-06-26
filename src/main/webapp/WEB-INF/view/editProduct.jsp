<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="adminHeader.jsp" %>
<%@ include file="footer.jsp" %>

<!DOCTYPE html>
<html>
<head>
 <link rel="stylesheet" href="<c:url value='/css/addNewProduct-style.css'/>">
 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
 <title>Edit Product</title>
 <style>
  .back {
   color: white;
   margin-left: 2.5%;
  }
 </style>
</head>
<body>
<h2><a class="back" href="<c:url value='/allProductEditProduct'/>"><i class='fas fa-arrow-circle-left'> Back</i></a></h2>

<form action="<c:url value='/editProductAction'/>" method="post">
 <input type="hidden" name="id" value="${product.id}">
 <div class="left-div">
  <h3>Enter Name</h3>
  <input class="input-style" type="text" name="name" value="${product.name}" required>
  <hr>
 </div>

 <div class="right-div">
  <h3>Enter Category</h3>
  <input class="input-style" type="text" name="category" value="${product.category}" required>
  <hr>
 </div>

 <div class="left-div">
  <h3>Enter Price</h3>
  <input class="input-style" type="number" name="price" value="${product.price}" required>
  <hr>
 </div>

 <div class="right-div">
  <h3>Active</h3>
  <select class="input-style" name="active">
   <option value="Yes" ${product.active == 'Yes' ? 'selected' : ''}>Yes</option>
   <option value="No" ${product.active == 'No' ? 'selected' : ''}>No</option>
  </select>
  <hr>
 </div>
 <button class="button">Save <i class='far fa-arrow-alt-circle-right'></i></button>
</form>

</body>
<br><br><br>
</html>
