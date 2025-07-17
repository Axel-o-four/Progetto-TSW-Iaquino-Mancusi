<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <title>Errore 404 – Pagina non trovata</title>
  <link rel="icon" type="image/png" href="<%=request.getContextPath()%>/media/icon.png">
	<link href="<%= request.getContextPath() %>/css/error.css" rel="stylesheet" type="text/css">
</head>
<body>
  <div class="container">
    <h1>Errore 404 – Pagina non trovata</h1>
    <p>La pagina che stai cercando non esiste o è stata rimossa.</p>
    <p>URL richiesto: <strong><%= request.getAttribute("javax.servlet.error.request_uri") %></strong></p>
    <p>Prova a cercare nella <a href="<%= request.getContextPath() %>/CatalogView.jsp">home page</a>.</p>
  </div>
</body>
</html>
