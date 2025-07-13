<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="java.util.*,it.unisa.Model.UserBean" %>

<%
    @SuppressWarnings("unchecked")
    Collection<UserBean> users =
        (Collection<UserBean>) request.getAttribute("users");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Elenco Utenti Registrati</title>
    <link rel="icon" type="image/png" href="<%=request.getContextPath()%>/media/icon.png">
	<link href="<%= request.getContextPath() %>/css/utenti.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="page">
<div class="header">
	<%@ include file="/Header.jsp" %>
</div>
<div class="body">
    <h1>Elenco Utenti Registrati</h1>

    <table border="1">
        <tr>
            <th>Email</th>
            <th>Nome</th>
            <th>Cognome</th>
            <th>Data Nascita</th>
            <th>Genere</th>
            <th>Admin?</th>
        </tr>
        <%
            if (users != null && !users.isEmpty()) {
                for (UserBean u : users) {
        %>
        <tr>
            <td><%= u.getEmail() %></td>
            <td><%= u.getNome() %></td>
            <td><%= u.getCognome() %></td>
            <td><%= u.getDataNascita() %></td>
            <td><%= u.getGenere() %></td>
            <td><%= u.isAdmin() ? "Sì" : "No" %></td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="6" style="text-align:center">
                Nessun utente trovato.
            </td>
        </tr>
        <%
            }
        %>
    </table>


    </div>
    </div>
    <div class="footer">
        <%@ include file="/Footer.jsp" %>
    </div>
</body>
</html>
