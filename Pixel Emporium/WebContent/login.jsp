<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>
<!DOCTYPE html>
<html>
<head>
   <meta charset="UTF-8">
   <title>Login</title>
</head>
<body>
   <h1>Login</h1>
   
   <% 
       String message = (String) request.getAttribute("message");
       if(message != null) { 
   %>
       <p style="color:red;"><%= message %></p>
   <%
       }
   %>
   
   <form action="Login" method="post">
       Email: <input type="email" name="email" required /><br>
       Password: <input type="password" name="password" required /><br>
       <input type="submit" value="Accedi" />
   </form>
   
   <br>
   <a href="registration.jsp">Non sei registrato? Registrati qui</a>
   <%@ include file="Footer.jsp" %>
</body>
</html>
