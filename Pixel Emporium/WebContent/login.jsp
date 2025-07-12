<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>
<!DOCTYPE html>
<html>
<head>
   <meta charset="UTF-8">
   <title>Login</title>
   <style>
     .error {
       color: #c00;
       font-size: 0.9em;
       margin-left: 5px;
     }
     input:focus {
       outline: 2px solid #06f;
     }
   </style>
</head>
<body>
   <h1>Login</h1>
   
   <% 
       String message = (String) request.getAttribute("message");
       if (message != null) { 
   %>
       <p style="color:red;"><%= message %></p>
   <% } %>
   
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
   <a href="registration.jsp">Non sei registrato? Registrati qui</a>
   <%@ include file="Footer.jsp" %>

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
