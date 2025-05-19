<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Reset Password | Online Shopping</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
    <style>
        body{font-family:"Inter",sans-serif;background:#f2f4f7;display:flex;align-items:center;justify-content:center;height:100vh;margin:0}
        .auth-card{max-width:460px;width:100%;background:#fff;border-radius:12px;box-shadow:0 8px 32px rgba(0,0,0,.06)}
        .auth-header{background:#f76707;color:#fff;padding:2rem;text-align:center;border-radius:12px 12px 0 0}
        .auth-body{padding:2rem}
        .form-control:focus{box-shadow:none;border-color:#f76707}
        .btn-warning{background:#f76707;border:#f76707}
    </style>
</head>
<body>
<div class="auth-card">
    <div class="auth-header">
        <h2 class="mb-0">Forgot Password?</h2>
        <p class="small opacity-75 mb-0">We'll send you a reset link</p>
    </div>
    <div class="auth-body">
        <c:if test="${param.msg=='done'}">
            <div class="alert alert-success small">New password sent to your e-mail</div>
        </c:if>
        <c:if test="${param.msg=='invalid'}">
            <div class="alert alert-danger small">Invalid information – try again</div>
        </c:if>
        <form action="${pageContext.request.contextPath}/forgotPassword" method="post">
            <div class="mb-3">
                <label class="form-label">Registered E-mail</label>
                <input type="email" name="email" class="form-control" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Mobile Number</label>
                <input type="number" name="mobileNumber" class="form-control" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Security Question</label>
                <select name="securityQuestion" class="form-select" required>
                    <option value="What was your first car?">What was your first car?</option>
                    <option value="What is the name of your first pet?">Name of your first pet?</option>
                    <option value="What elementary school did you attend?">Elementary school you attended?</option>
                    <option value="What is the name of the town where you were born?">Town where you were born?</option>
                </select>
            </div>
            <div class="mb-3">
                <label class="form-label">Answer</label>
                <input name="answer" class="form-control" required>
            </div>
            <div class="mb-3">
                <label class="form-label">New Password</label>
                <input type="password" name="newPassword" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-warning w-100">Send Reset Link</button>
        </form>
        <hr>
        <p class="text-center small mb-0"><a href="${pageContext.request.contextPath}/login" class="text-decoration-none">Back to login</a></p>
    </div>
</div>
</body>
</html>