<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
   <meta charset="UTF-8">
   <title>Login - Pixel Emporium</title>
   <link rel="icon" type="image/png" href="../media/icon.png">
   <link href="css/login.css" type="text/css" rel="stylesheet">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<div class="page">
	<div class="header">
		<%@ include file="Header.jsp" %>
	</div>
   <div class="body">
   <h1>Login</h1>
   <% 
       String message = (String) request.getAttribute("message");
       if (message != null) { 
   %>
       <p style="color:red;"><%= message %></p>
   <% } %>
   <div class="form">
   <form id="loginForm" action="Login" method="post" novalidate>
       <label for="email">Email:</label>
       <input 
         type="email" 
         id="email" 
         name="email" 
         placeholder="esempio@dominio.com" 
         required
       >
       <span class="error" id="emailError"></span><br><br>
    
       <label for="password">Password:</label>
       <input 
         type="password" 
         id="password" 
         name="password" 
         placeholder="Minimo 6 caratteri" 
         required
       >
       <span class="error" id="passwordError"></span><br><br>
    
       <input type="submit" value="Accedi">
   </form>
   
   <br>
   <a href="registration.jsp" id="registration">Non sei registrato? Registrati qui</a>
   
</div>
</div>
</div>
<div class="footer">
<%@ include file="Footer.jsp" %>
</div>
   <script>
   document.addEventListener('DOMContentLoaded', function() {
     var form     = document.getElementById('loginForm');
     var email    = document.getElementById('email');
     var password = document.getElementById('password');

     var emailError    = document.getElementById('emailError');
     var passwordError = document.getElementById('passwordError');

     email.addEventListener('input', function() {
       emailError.textContent = '';
     });
     password.addEventListener('input', function() {
       passwordError.textContent = '';
     });

     form.addEventListener('submit', function(e) {
       if (!email.checkValidity()) {
         e.preventDefault();
         emailError.textContent = 'Email non valida';
         email.focus();
         return;
       }

       if (password.value.trim().length < 6) {
         e.preventDefault();
         passwordError.textContent = 'Deve contenere almeno 6 caratteri';
         password.focus();
         return;
       }
     });
   });
   </script>
</body>
</html>
