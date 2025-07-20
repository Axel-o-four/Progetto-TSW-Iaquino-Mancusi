<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.Model.GiocoBean" %>

<%
    GiocoBean gioco = (GiocoBean) request.getAttribute("gioco");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dettagli Videogioco - Pixel Emporium</title>
    <link rel="icon" type="image/png" href="<%=request.getContextPath()%>/media/icon.png">
    <link href="css/dettagli.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="page">
<div class="header">
	<%@ include file="Header.jsp" %>
</div>
<div class="body">
    <h1>Dettagli Videogioco</h1>
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
                <img src="<%= request.getContextPath() + "/" + gioco.getImage() %>"
                     alt="<%= gioco.getName() %>"
                     style="max-height:200px; max-width:200px;">
            </td>
            <td><%= gioco.getBrand() %></td>
            <td><%= String.format("%.2f", gioco.getPrice()) %>€</td>
            <td><%= gioco.getReleaseYear() %></td>
            <td><%= gioco.getGenre() %></td>
            <td><%= gioco.getPegi() %></td>
            <td><%= gioco.getFormat() %></td>
            <td><%= gioco.getQuantity() %></td>
        </tr>
    </table>
    
    <h3>Aggiungi al Carrello</h3>
    <form id="addToCartForm" action="GiocoControl" method="get" novalidate>
        <input type="hidden" name="action" value="addC">
        <input type="hidden" name="id" value="<%= gioco.getCode() %>">

        <label for="quantity">Quantità:</label>
        <input
          id="quantity"
          name="quantity"
          type="number"
          min="1"
          value="1"
          placeholder="Inserisci un intero positivo"
          style="width:50px"
        >
        <span id="quantityError" class="error"></span>

        <input type="submit" value="Aggiungi">
    </form>
    <% } else { %>
       <p>Nessun dettaglio disponibile per questo videogioco.</p>
    <% } %>
    

    <script>
      document.addEventListener("DOMContentLoaded", function() {
        var form = document.getElementById("addToCartForm");
        var quantity = document.getElementById("quantity");
        var errorSpan = document.getElementById("quantityError");
        var positiveInteger = /^[1-9]\d*$/;

        quantity.addEventListener("input", function() {
          errorSpan.textContent = "";
        });

        form.addEventListener("submit", function(evt) {
          var val = quantity.value.trim();
          if (!positiveInteger.test(val)) {
            evt.preventDefault();
            errorSpan.textContent = "Devi inserire almeno 1.";
            quantity.focus();
          }
        });
      });
    </script>
</div>
</div>
<div class=footer">
    <%@ include file="Footer.jsp" %>
</div>
</body>
</html>
