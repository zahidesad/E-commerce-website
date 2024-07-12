<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Error Page</title>
</head>
<body>
<div class="container">
    <h2>Error Page</h2>
    <p>An unexpected error occurred. Please try again later.</p>
    <c:if test="${not empty requestScope['jakarta.servlet.error.message']}">
        <h3>Error Message:</h3>
        <pre>${requestScope['jakarta.servlet.error.message']}</pre>
    </c:if>
    <c:if test="${not empty requestScope['jakarta.servlet.error.exception']}">
        <h3>Exception:</h3>
        <pre>${requestScope['jakarta.servlet.error.exception']}</pre>
        <h4>Stack Trace:</h4>
        <pre>
                <c:forEach var="traceElement" items="${requestScope['jakarta.servlet.error.exception'].stackTrace}">
                    ${traceElement}
                </c:forEach>
            </pre>
    </c:if>
</div>
</body>
</html>
