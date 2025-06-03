<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="Header.jsp" %>
<!DOCTYPE html>
<html>
<head>
   <meta charset="UTF-8">
   <title>Registrazione Utente</title>
</head>
<body>
   <h1>Registrazione</h1>
   
   <% 
       String message = (String) request.getAttribute("message");
       if(message != null) { 
   %>
       <p style="color:red;"><%= message %></p>
   <%
       }
   %>
   
   <form action="Register" method="post">
       Email: <input type="email" name="email" required /><br>
       Nome: <input type="text" name="nome" required /><br>
       Cognome: <input type="text" name="cognome" required /><br>
       Data Nascita (yyyy-MM-dd): <input type="date" name="dataNascita" required /><br>
       Genere (M/F): <input type="text" name="genere" maxlength="1" /><br>
       Indirizzo: <input type="text" name="indirizzo" required /><br>
       Città: <input type="text" name="citta" required /><br>
       Provincia: <input type="text" name="prov" required maxlength="2" /><br>
       CAP: <input type="text" name="cap" required maxlength="5" /><br>
       Password: <input type="password" name="password" required /><br>
       <input type="submit" value="Registrati" />
   </form>
   
   <br>
   <a href="login.jsp">Sei già registrato? Vai al login</a>
   <%@ include file="Footer.jsp" %>
</body>
</html>
