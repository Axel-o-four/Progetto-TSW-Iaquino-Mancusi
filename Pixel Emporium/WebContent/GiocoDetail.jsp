<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.GiocoBean" %>
<%@ include file="Header.jsp" %>

<%
    GiocoBean gioco = (GiocoBean) request.getAttribute("gioco");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dettagli Videogioco - Pixel Emporium</title>
    <link href="ProductStyle.css" rel="stylesheet" type="text/css">
</head>
<body>
    <h2>Dettagli Videogioco</h2>
    <% if (gioco != null) { %>
    <table border="1">
        <tr>
            <th>Prefisso</th>
            <th>Code</th>
            <th>Nome</th>
            <th>Descrizione</th>
            <th>Immagine</th>
            <th>Marchio</th>
            <th>Prezzo</th>
            <th>Anno di Rilascio</th>
            <th>Genere</th>
            <th>PEGI</th>
            <th>Formato</th>
            <th>Quantità</th>
        </tr>
        <tr>
            <td><%= gioco.getPrefissoId() %></td>
            <td><%= gioco.getCode() %></td>
            <td><%= gioco.getName() %></td>
            <td><%= gioco.getDescription() %></td>
            <td>
                <img src="<%= request.getContextPath() + "/" + gioco.getImage() %>" alt="<%= gioco.getName() %>" style="max-height:200px; max-width:200px;">
            </td>
            <td><%= gioco.getBrand() %></td>
            <td><%= gioco.getPrice() %>€</td>
            <td><%= gioco.getReleaseYear() %></td>
            <td><%= gioco.getGenre() %></td>
            <td><%= gioco.getPegi() %></td>
            <td><%= gioco.getFormat() %></td>
            <td><%= gioco.getQuantity() %></td>
        </tr>
    </table>
    
    <h3>Aggiungi al Carrello</h3>
    <form action="GiocoControl" method="get">
        <input type="hidden" name="action" value="addC">
        <input type="hidden" name="id" value="<%= gioco.getCode() %>">
        <label for="quantity">Quantità: </label>
        <input type="number" name="quantity" min="1" value="1" style="width:50px">
        <input type="submit" value="Aggiungi">
    </form>
    <% } else { %>
       <p>Nessun dettaglio disponibile per questo videogioco.</p>
    <% } %>
    
    <%@ include file="Footer.jsp" %>
</body>
</html>