<!DOCTYPE html>
<html>
<head>
   <meta charset="UTF-8">
   <title>Accesso Negato</title>
</head>
<body>
   <h1>Accesso Negato!</h1>
   <p>Non sei autorizzato ad accedere a questa pagina.</p>
   <a href="<%= request.getContextPath() %>/login.jsp">Vai al login</a>
</body>
</html>
