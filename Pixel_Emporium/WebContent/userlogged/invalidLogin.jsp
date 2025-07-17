<!DOCTYPE html>
<html>
<head>
   <meta charset="UTF-8">
   <title>Accesso Negato - Pixel Emporium</title>
   	<link rel="icon" type="image/png" href="<%=request.getContextPath()%>/media/icon.png">
	<link href="<%= request.getContextPath() %>/css/denied.css" rel="stylesheet" type="text/css">
</head>
<body>
   <h1>Accesso Negato!</h1>
   <p>Non sei autorizzato ad accedere a questa pagina.</p>
   <a href="<%= request.getContextPath() %>/login.jsp">Vai al login</a>
</body>
</html>
