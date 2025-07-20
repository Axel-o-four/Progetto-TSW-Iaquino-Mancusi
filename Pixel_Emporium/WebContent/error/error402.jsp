<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <title>Errore 402 – Pagamento richiesto</title>
  <link rel="icon" type="image/png" href="<%=request.getContextPath()%>/media/icon.png">
	<link href="<%= request.getContextPath() %>/css/error.css" rel="stylesheet" type="text/css">
</head>
<body>
  <div class="container">
    <h1>Errore 402 – Pagamento richiesto</h1>
    <p>Questa risorsa richiede un pagamento. Contatta l’amministratore per maggiori informazioni.</p>
    <p>Torna alla <a href="<%= request.getContextPath() %>/CatalogView.jsp">home page</a>.</p>
  </div>
</body>
</html>
