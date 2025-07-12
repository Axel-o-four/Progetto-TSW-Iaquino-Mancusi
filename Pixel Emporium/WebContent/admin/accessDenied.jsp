<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ include file="/Header.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Accesso Negato - Area Amministratore</title>
    <link href="<%= request.getContextPath() %>/ProductStyle.css" rel="stylesheet" type="text/css">
</head>
<body>
    <h2>Accesso Negato</h2>
    <p>Non hai i permessi necessari per visualizzare questa pagina.</p>
    <p>
        <a href="<%= request.getContextPath() %>/CatalogView.jsp">Torna al catalogo</a>
        &nbsp;|&nbsp;
    </p>
</body>
</html>

<%@ include file="/Footer.jsp" %>
