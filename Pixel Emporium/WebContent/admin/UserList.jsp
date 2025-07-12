<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="java.util.*,it.unisa.Model.UserBean" %>
<%@ include file="/Header.jsp" %>

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
    <link href="<%= request.getContextPath() %>/ProductStyle.css"
          rel="stylesheet" type="text/css">
</head>
<body>
    <h2>Elenco Utenti Registrati</h2>

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

    <%@ include file="/Footer.jsp" %>
</body>
</html>
