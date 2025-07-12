<%@ page import="it.unisa.Model.UserBean" %>
<%@ include file="/Header.jsp" %>
<%
    UserBean user = (UserBean) session.getAttribute("user");
    if (user == null) {
    	response.sendRedirect(request.getContextPath() + "/userlogged/invalidLogin.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
   <meta charset="UTF-8">
   <title>Area Utente</title>
</head>
<body>
   <h1>Benvenuto, <%= user.getNome() %>!</h1>
   <p>Questa è una pagina riservata agli utenti registrati.</p>
   <a href="<%= request.getContextPath() %>/CatalogView.jsp">Torna al catalogo</a>
   <br/>
   <a href="<%= request.getContextPath() %>/Logout">Logout</a>
</body>
</html>
