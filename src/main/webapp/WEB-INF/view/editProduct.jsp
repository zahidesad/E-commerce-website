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
   <label for="name">Name:</label>
   <input type="text" id="name" name="name" value="${product.name}" required>
  </div>
  <div class="form-group">
   <label for="category">Category:</label>
   <select id="category" name="categoryId" required>
    <c:forEach var="category" items="${categories}">
     <option value="${category.id}"
             <c:if test="${not empty product.categories && product.categories.size() > 0 && category.id == product.categories[0].id}">selected</c:if>>
       ${category.name}
     </option>
    </c:forEach>
   </select>
  </div>
  <div class="form-group">
   <label for="active">Active:</label>
   <select id="active" name="active">
    <option value="Yes" <c:if test="${product.active == 'Yes'}">selected</c:if>>Yes</option>
    <option value="No" <c:if test="${product.active == 'No'}">selected</c:if>>No</option>
   </select>
  </div>
  <div class="form-group">
   <label for="quantity">Quantity:</label>
   <input type="number" id="quantity" name="quantity" value="${product.stocks[0].quantity}" required>
  </div>
  <h3>Current Prices:</h3>
  <c:forEach var="price" items="${product.prices}">
   <input type="hidden" name="priceId" value="${price.id}">
   <div class="form-group">
    <label for="price_${price.id}">Price:</label>
    <input type="number" id="price_${price.id}" name="priceAmount" value="${price.price}" required>
   </div>
   <div class="form-group">
    <label for="startDate_${price.id}">Start Date:</label>
    <input type="date" id="startDate_${price.id}" name="startDate" value="${price.startDate}" required>
   </div>
   <div class="form-group">
    <label for="endDate_${price.id}">End Date:</label>
    <input type="date" id="endDate_${price.id}" name="endDate" value="${price.endDate}" required>
   </div>
  </c:forEach>
  <button class="btn" type="submit">Save <i class='far fa-arrow-alt-circle-right'></i></button>
 </form>
</div>
</body>
</html>
