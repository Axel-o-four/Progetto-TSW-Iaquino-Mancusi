<%@ page import="it.unisa.Model.UserBean" %>
<header>
  <h1>Pixel Emporium</h1>
  <nav>
    <a href="<%= request.getContextPath() %>/CatalogView.jsp">Catalogo</a> |
    <a href="<%= request.getContextPath() %>/CartView.jsp">Carrello</a> |
    <%
      UserBean loggedUser = (UserBean) session.getAttribute("user");
      if (loggedUser != null && loggedUser.isAdmin()) {
    %>
      <a href="<%= request.getContextPath() %>/admin/AdminView.jsp">Gestione Catalogo</a> |
      <a href="<%= request.getContextPath() %>/admin/users">Utenti</a> |
      <a href="<%= request.getContextPath() %>/admin/orders">Ordini</a> |
    <%
      }
    %>
    <%
      if (loggedUser == null) {
    %>
      <a href="<%= request.getContextPath() %>/login.jsp">Accedi</a>
    <%
      } else {
    %>
      <a href="<%= request.getContextPath() %>/userlogged/UserProfile.jsp">Profilo</a> |
      <a href="<%= request.getContextPath() %>/OrdineControl">I miei ordini</a> |
      <a href="<%= request.getContextPath() %>/Logout">Logout</a>
    <%
      }
    %>
  </nav>
</header>