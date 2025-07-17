<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <title>Errore 501 – Non implementato</title>
  <link rel="icon" type="image/png" href="<%=request.getContextPath()%>/media/icon.png">
	<link href="<%= request.getContextPath() %>/css/error.css" rel="stylesheet" type="text/css">
</head>
<body>
  <div class="container">
    <h1>Errore 501 – Funzionalità non implementata</h1>
    <p>Questa funzionalità non è ancora disponibile. Rimani aggiornato per i prossimi rilasci.</p>
    <p>Torna alla <a href="<%= request.getContextPath() %>/CatalogView.jsp">home page</a>.</p>
  </div>
</body>
</html>
