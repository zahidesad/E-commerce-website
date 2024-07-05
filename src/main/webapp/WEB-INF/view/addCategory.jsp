<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="adminHeader.jsp" %>
<%@ include file="footer.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Add Category</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/addNewProduct-style.css">
</head>
<body>
<div class="container">
    <h2>Add New Category <i class="fas fa-plus-circle"></i></h2>
    <form action="${pageContext.request.contextPath}/addCategory" method="post">
        <div class="form-group">
            <label for="name">Category Name:</label>
            <input type="text" id="name" name="name" required>
        </div>
        <button type="submit" class="btn">Add Category</button>
    </form>
    <br>
    <a href="${pageContext.request.contextPath}/manageCategoryRelationships" class="btn">Manage Category Relationship</a>
</div>
</body>
</html>
