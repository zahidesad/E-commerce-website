<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Sign Up | Online Shopping</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
  <style>
    body{font-family:"Inter",sans-serif;background:#f2f4f7;display:flex;align-items:start;justify-content:center;padding:3rem 1rem}
    .auth-card{max-width:520px;width:100%;background:#fff;border-radius:12px;box-shadow:0 8px 32px rgba(0,0,0,.06)}
    .auth-header{background:#12b886;color:#fff;padding:2rem;text-align:center;border-radius:12px 12px 0 0}
    .auth-body{padding:2rem 2.5rem}
    .form-control:focus{box-shadow:none;border-color:#12b886}
    .btn-success{background:#12b886;border:#12b886}
  </style>
</head>
<body>
<div class="auth-card">
  <div class="auth-header">
    <h2 class="mb-0">Create Account</h2>
    <p class="small opacity-75 mb-0">It’s free and only takes a minute</p>
  </div>
  <div class="auth-body">
    <c:if test="${msg=='email_in_use'}">
      <div class="alert alert-danger small">E-mail is already registered</div>
    </c:if>
    <form action="${pageContext.request.contextPath}/register" method="post">
      <div class="row g-3">
        <div class="col-sm-6">
          <label class="form-label">Name</label>
          <input name="name" class="form-control" required>
        </div>
        <div class="col-sm-6">
          <label class="form-label">Surname</label>
          <input name="surname" class="form-control" required>
        </div>
        <div class="col-12">
          <label class="form-label">TC Number</label>
          <input name="tcNumber" class="form-control" required>
        </div>
        <div class="col-12 col-md-6">
          <label class="form-label">Birth Year</label>
          <input name="birthYear" type="number" class="form-control" required>
        </div>
        <div class="col-12 col-md-6">
          <label class="form-label">Mobile</label>
          <input name="mobileNumber" type="number" class="form-control" required>
        </div>
        <div class="col-12">
          <label class="form-label">E-mail</label>
          <input name="email" type="email" class="form-control" required>
        </div>
        <div class="col-12">
          <label class="form-label">Security Question</label>
          <select name="securityQuestion" class="form-select" required>
            <option value="What was your first car?">What was your first car?</option>
            <option value="What is the name of your first pet?">Name of your first pet?</option>
            <option value="What elementary school did you attend?">Elementary school you attended?</option>
            <option value="What is the name of the town where you were born?">Town where you were born?</option>
          </select>
        </div>
        <div class="col-12">
          <label class="form-label">Answer</label>
          <input name="answer" class="form-control" required>
        </div>
        <div class="col-12">
          <label class="form-label">Password</label>
          <input name="password" type="password" minlength="6" class="form-control" required>
        </div>
        <div class="col-12">
          <button class="btn btn-success w-100" type="submit">Sign Up</button>
        </div>
      </div>
    </form>
    <hr>
    <p class="text-center small mb-0">Already have an account? <a href="${pageContext.request.contextPath}/login" class="text-decoration-none">Login</a></p>
  </div>
</div>
</body>
</html>