<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="adminHeader.jsp" %>
<%@ include file="footer.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Manage Category Relationship</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/addNewProduct-style.css">
    <script>
        function validateForm() {
            var parentCategory = document.getElementById("parentCategory").value;
            var childCategory = document.getElementById("childCategory").value;
            if (parentCategory === childCategory) {
                alert("Parent Category and Child Category cannot be the same.");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<div class="container">
    <h2>Manage Category Relationship <i class="fas fa-sitemap"></i></h2>
    <form action="${pageContext.request.contextPath}/manageCategoryRelationship" method="post" onsubmit="return validateForm()">
        <div class="form-group">
            <label for="parentCategory">Parent Category:</label>
            <select id="parentCategory" name="parentCategory" required>
                <c:forEach var="category" items="${categories}">
                    <option value="${category.id}">${category.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="childCategory">Child Category:</label>
            <select id="childCategory" name="childCategory" required>
                <c:forEach var="category" items="${categories}">
                    <option value="${category.id}">${category.name}</option>
                </c:forEach>
            </select>
        </div>
        <button type="submit" class="btn">Set Relationship</button>
    </form>
</div>
</body>
</html>
