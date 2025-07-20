<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <title>Errore 403 – Accesso vietato</title>
  <link rel="icon" type="image/png" href="<%=request.getContextPath()%>/media/icon.png">
	<link href="<%= request.getContextPath() %>/css/error.css" rel="stylesheet" type="text/css">
</head>
<body>
  <div class="container">
    <h1>Errore 403 – Accesso vietato</h1>

    <p>Non hai i permessi necessari per visualizzare questa risorsa.</p>

    <p>
      Se ritieni che sia un errore, contatta l’amministratore di sistema.  
      Torna alla <a href="<%= request.getContextPath() %>/CatalogView.jsp">home page</a>.
    </p>
  </div>
</body>
</html>
