<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="adminHeader.jsp" %>
<%@ include file="footer.jsp" %>

<!DOCTYPE html>
<html>
<head>
 <title>Add New Product</title>
 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/addNewProduct-style.css">
</head>
<body>
<div class="container">
 <h2>Add New Product <i class="fas fa-plus-circle"></i></h2>
 <form action="${pageContext.request.contextPath}/addNewProduct" method="post">
  <div class="form-group">
   <label for="name">Name:</label>
   <input type="text" id="name" name="name" required>
  </div>
  <div class="form-group">
   <label for="category">Category:</label>
   <input type="text" id="category" name="category" required>
  </div>
  <div class="form-group">
   <label for="price">Price:</label>
   <input type="number" id="price" name="price" required>
  </div>
  <div class="form-group">
   <label for="active">Active:</label>
   <select id="active" name="active" required>
    <option value="Yes">Yes</option>
    <option value="No">No</option>
   </select>
  </div>
  <button type="submit" class="btn">Add Product</button>
 </form>
</div>
</body>
</html>
