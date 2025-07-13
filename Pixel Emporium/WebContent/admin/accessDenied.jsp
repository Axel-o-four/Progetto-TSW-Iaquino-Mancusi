<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Accesso Negato - Area Amministratore - Pixel Emporium</title>
    <link rel="icon" type="image/png" href="<%=request.getContextPath()%>/media/icon.png">
	<link href="<%= request.getContextPath() %>/css/denied.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="page">
<div class="header">
	<%@ include file="/Header.jsp" %>
</div>
<div class="body">
    <h1>Accesso Negato</h1>
    <p>Non hai i permessi necessari per visualizzare questa pagina.</p>
        <a href="<%= request.getContextPath() %>/CatalogView.jsp">Torna al catalogo</a>
    </div>
    </div>
    <div class="footer">
    <%@ include file="/Footer.jsp" %>
    
    </div>
</body>
</html>

