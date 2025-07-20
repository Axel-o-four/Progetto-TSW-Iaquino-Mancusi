<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <title>Errore 400 – Richiesta non valida</title>
  <link rel="icon" type="image/png" href="<%=request.getContextPath()%>/media/icon.png">
	<link href="<%= request.getContextPath() %>/css/error.css" rel="stylesheet" type="text/css">
</head>
<body>
  <div class="container">
    <h1>Errore 400 – Richiesta non valida</h1>
    <p>La tua richiesta non è valida o è malformata. Controlla i parametri e riprova.</p>
    <p>Torna alla <a href="<%= request.getContextPath() %>/CatalogView.jsp">home page</a>.</p>
  </div>
</body>
</html>
