<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="adminHeader.jsp" %>
<%@ include file="footer.jsp" %>

<!DOCTYPE html>
<html>
<head>
 <link rel="stylesheet" href="<c:url value='/css/edit-product-style.css'/>">
 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
 <title>Edit Product</title>
</head>
<body>
<div class="container">
 <h2>Edit Product <i class="fas fa-edit"></i></h2>
 <form action="<c:url value='/updateProduct'/>" method="post">
  <input type="hidden" name="id" value="${product.id}">
  <div class="form-group">
   <input type="text" name="name" value="${product.name}" required>
  </div>
  <div class="form-group">
   <input type="text" name="category" value="${product.category}" required>
  </div>
  <div class="form-group">
   <input type="number" name="price" value="${product.price}" required>
  </div>
  <div class="form-group">
   <select name="active">
    <option value="Yes" ${product.active == 'Yes' ? 'selected' : ''}>Yes</option>
    <option value="No" ${product.active == 'No' ? 'selected' : ''}>No</option>
   </select>
  </div>
  <button class="btn">Save <i class='far fa-arrow-alt-circle-right'></i></button>
 </form>
</div>
</body>
</html>
