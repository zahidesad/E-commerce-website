<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="adminHeader.jsp" %>
<%@ include file="footer.jsp" %>
<!DOCTYPE html>
<html>
<head>
 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/addNewProduct-style.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home-style.css">
 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
 <title>Add New Product</title>
</head>
<body>

<c:if test="${not empty message}">
 <h3 class="alert">${message}</h3>
</c:if>

<form action="${pageContext.request.contextPath}/addNewProduct" method="post">
 <h3 style="color: yellow;">Product ID: ${productId} </h3>
 <input type="hidden" name="id" value="${productId}">

 <div class="left-div">
  <h3>Enter Name</h3>
  <input class="input-style" type="text" name="name" placeholder="Enter Name" required>
  <hr>
 </div>

 <div class="right-div">
  <h3>Enter Category</h3>
  <input class="input-style" type="text" name="category" placeholder="Enter Category" required>
  <hr>
 </div>

 <div class="left-div">
  <h3>Enter Price</h3>
  <input class="input-style" type="number" name="price" placeholder="Enter Price" required>
  <hr>
 </div>

 <div class="right-div">
  <h3>Active</h3>
  <select class="input-style" name="active">
   <option value="Yes">Yes</option>
   <option value="No">No</option>
  </select>
  <hr>
 </div>

 <button class="button" type="submit">Save <i class="far fa-arrow-alt-circle-right"></i></button>
</form>

<br><br><br>
</body>
</html>
