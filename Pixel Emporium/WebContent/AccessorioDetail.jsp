<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.AccessorioBean" %>
<%@ include file="Header.jsp" %>

<%
    AccessorioBean accessorio = (AccessorioBean) request.getAttribute("accessorio");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dettagli Accessorio - Pixel Emporium</title>
    <link href="ProductStyle.css" rel="stylesheet" type="text/css">
</head>
<body>
    <h2>Dettagli Accessorio</h2>
    <% if (accessorio != null) { %>
    <table border="1">
        <tr>
            <th>Prefisso</th>
            <th>Code</th>
            <th>Nome</th>
            <th>Descrizione</th>
            <th>Immagine</th>
            <th>Marchio</th>
            <th>Prezzo</th>
            <th>Tipo Accessorio</th>
            <th>Quantità</th>
        </tr>
        <tr>
            <td><%= accessorio.getPrefissoId() %></td>
            <td><%= accessorio.getCode() %></td>
            <td><%= accessorio.getName() %></td>
            <td><%= accessorio.getDescription() %></td>
            <td>
                <img src="<%= request.getContextPath() + "/" + accessorio.getImage() %>" alt="<%= accessorio.getName() %>" style="max-height:200px; max-width:200px;">
            </td>
            <td><%= accessorio.getBrand() %></td>
            <td><%= accessorio.getPrice() %>€</td>
            <td><%= accessorio.getAccessoryType() %></td>
            <td><%= accessorio.getQuantity() %></td>
        </tr>
    </table>
    
    <h3>Aggiungi al Carrello</h3>
    <form action="AccessorioControl" method="get">
        <input type="hidden" name="action" value="addC">
        <input type="hidden" name="id" value="<%= accessorio.getCode() %>">
        <label for="quantity">Quantità: </label>
        <input type="number" name="quantity" min="1" value="1" style="width:50px">
        <input type="submit" value="Aggiungi">
    </form>
    <% } else { %>
       <p>Nessun dettaglio disponibile per questo accessorio.</p>
    <% } %>
    <%@ include file="Footer.jsp" %>
</body>
</html>
