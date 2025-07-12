<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.Model.ConsoleBean" %>
<%@ include file="Header.jsp" %>

<%
    ConsoleBean console = (ConsoleBean) request.getAttribute("console");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dettagli Console - Pixel Emporium</title>
    <link href="ProductStyle.css" rel="stylesheet" type="text/css">

    <style>
      .error {
        color: #c00;
        font-size: 0.9em;
        margin-left: 5px;
      }
      input:focus {
        outline: 2px solid #06f;
      }
    </style>
</head>
<body>
    <h2>Dettagli Console</h2>
    <% if (console != null) { %>
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
        <th>Supporti</th>
        <th>Retrocompatibilità</th>
        <th>Archiviazione</th>
        <th>Generazione</th>
        <th>Quantità</th>
      </tr>
      <tr>
        <td><%= console.getPrefissoId() %></td>
        <td><%= console.getCode() %></td>
        <td><%= console.getName() %></td>
        <td><%= console.getDescription() %></td>
        <td>
          <img src="<%= request.getContextPath() + "/" + console.getImage() %>"
               alt="<%= console.getName() %>"
               style="max-height:200px; max-width:200px;">
        </td>
        <td><%= console.getBrand() %></td>
        <td><%= console.getPrice() %>€</td>
        <td><%= console.getReleaseYear() %></td>
        <td><%= console.getSupport() %></td>
        <td><%= console.isRetroCompatibility() ? "Sì" : "No" %></td>
        <td><%= console.getStorage() %></td>
        <td><%= console.getGeneration() %></td>
        <td><%= console.getQuantity() %></td>
      </tr>
    </table>

    <h3>Aggiungi al Carrello</h3>
    <form id="addToCartForm" action="ConsoleControl" method="get" novalidate>
      <input type="hidden" name="action" value="addC">
      <input type="hidden" name="id" value="<%= console.getCode() %>">

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
      <p>Nessun dettaglio disponibile per questa console.</p>
    <% } %>
    <%@ include file="Footer.jsp" %>

    <script>
      document.addEventListener("DOMContentLoaded", function() {
        const form = document.getElementById("addToCartForm");
        const qty  = document.getElementById("quantity");
        const err  = document.getElementById("quantityError");
        const pattern = /^[1-9]\d*$/;

        qty.addEventListener("input", () => {
          err.textContent = "";
        });

        form.addEventListener("submit", function(evt) {
          if (!pattern.test(qty.value.trim())) {
            evt.preventDefault();
            err.textContent = "Devi inserire un intero positivo.";
            qty.focus();
          }
        });
      });
    </script>
</body>
</html>

