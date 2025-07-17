<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <title>Errore 500 – Errore interno del server</title>
  <link rel="icon" type="image/png" href="<%=request.getContextPath()%>/media/icon.png">
	<link href="<%= request.getContextPath() %>/css/error.css" rel="stylesheet" type="text/css">
</head>
<body>
  <div class="container">
    <h1>Errore 500 – Errore interno del server</h1>
    <p>Si è verificato un problema interno. Ti invitiamo a riprovare più tardi.</p>
    <p>Se il problema persiste, contatta il supporto.</p>
    
    <% 
      Throwable t = (Throwable) request.getAttribute("javax.servlet.error.exception");
      if (t != null) { 
    %>
      <h2>Dettagli eccezione:</h2>
      <pre style="text-align:left; background:#eee; padding:10px;">
	<% 
        for (StackTraceElement el : t.getStackTrace()) { 
	%>
	<%= el.toString() %>
	<%   
        } 
	%>
      </pre>
    <% } %>
    
    <p>Torna alla <a href="<%= request.getContextPath() %>/CatalogView.jsp">home page</a>.</p>
  </div>
</body>
</html>
