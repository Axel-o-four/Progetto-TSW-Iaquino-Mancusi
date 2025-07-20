<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <title>Errore 401 – Non autorizzato</title>
  <link rel="icon" type="image/png" href="<%=request.getContextPath()%>/media/icon.png">
	<link href="<%= request.getContextPath() %>/css/error.css" rel="stylesheet" type="text/css">
</head>
<body>
  <div class="container">
    <h1>Errore 401 – Accesso negato</h1>
    <p>Devi essere autenticato per accedere a questa risorsa.</p>
    <p><a href="<%= request.getContextPath() %>/login.jsp">Vai al login</a></p>
  </div>
</body>
</html>
