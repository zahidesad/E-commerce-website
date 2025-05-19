<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login | Online Shopping</title>
    <!-- Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
    <style>
        body{font-family:"Inter",sans-serif;background:#f2f4f7;display:flex;align-items:center;justify-content:center;height:100vh;margin:0}
        .auth-card{max-width:420px;width:100%;background:#fff;border-radius:12px;box-shadow:0 8px 32px rgba(0,0,0,.06)}
        .auth-header{background:linear-gradient(135deg,#4c6ef5,#3b5bfd);padding:2.5rem;text-align:center;color:#fff;border-radius:12px 12px 0 0}
        .auth-header h1{font-size:1.75rem;font-weight:600;margin:0}
        .auth-body{padding:2rem}
        .form-control:focus{box-shadow:none;border-color:#4c6ef5}
        .btn-primary{background:#4c6ef5;border:#4c6ef5}
        .divider{height:1px;background:#e9ecef;margin:1.5rem 0}
    </style>
</head>
<body>
<div class="auth-card">
    <div class="auth-header">
        <h1>Welcome&nbsp;Back</h1>
        <p class="small opacity-75 mb-0">Log in to continue shopping</p>
    </div>
    <div class="auth-body">
        <c:if test="${param.error != null}">
            <div class="alert alert-danger small mb-3">
                <c:choose>
                    <c:when test="${param.error eq 'bad_credentials'}">Wrong e-mail / password</c:when>
                    <c:when test="${param.error eq 'disabled'}">Account not enabled – verify your mail.</c:when>
                </c:choose>
            </div>
        </c:if>
        <form action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
            <div class="mb-3">
                <label class="form-label" for="j_username">E-mail</label>
                <input type="email" id="j_username" name="j_username" class="form-control" placeholder="example@mailprovider.com" required>
            </div>
            <div class="mb-3">
                <label class="form-label" for="j_password">Password</label>
                <input type="password" id="j_password" name="j_password" class="form-control" placeholder="••••••••" required>
            </div>
            <button class="btn btn-primary w-100" type="submit">Login</button>
        </form>
        <div class="divider"></div>
        <div class="d-flex justify-content-between small">
            <a href="${pageContext.request.contextPath}/forgotPassword" class="text-decoration-none">Forgot password?</a>
            <a href="${pageContext.request.contextPath}/register" class="text-decoration-none">Create account</a>
        </div>
    </div>
</div>
</body>
</html>