<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.Model.AccessorioBean" %>


<%
    AccessorioBean accessorio = (AccessorioBean) request.getAttribute("accessorio");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dettagli Accessorio - Pixel Emporium</title>
    <link rel="icon" type="image/png" href="<%=request.getContextPath()%>/media/icon.png">
    <link href="css/dettagli.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="page">
<div class="header">
	<%@ include file="Header.jsp" %>
</div>
<div class="body">
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
                <img src="<%= request.getContextPath() + "/" + accessorio.getImage() %>" 
                     alt="<%= accessorio.getName() %>" 
                     style="max-height:200px; max-width:200px;">
            </td>
            <td><%= accessorio.getBrand() %></td>
            <td><%= String.format("%.2f", accessorio.getPrice()) %>€</td>
            <td><%= accessorio.getAccessoryType() %></td>
            <td><%= accessorio.getQuantity() %></td>
        </tr>
    </table>
    
    <h3>Aggiungi al Carrello</h3>
    <form id="addToCartForm" action="AccessorioControl" method="get" novalidate>
        <input type="hidden" name="action" value="addC">
        <input type="hidden" name="id" value="<%= accessorio.getCode() %>">

        <label for="quantity">Quantità:</label>
        <input 
            id="quantity" 
            name="quantity" 
            type="number" 
            min="1" 
            value="1" 
            placeholder="Inserisci numero intero positivo" 
            style="width:50px"
        >
        <span id="quantityError" class="error"></span>

        <input type="submit" value="Aggiungi">
    </form>

    <script>
    document.addEventListener("DOMContentLoaded", function() {
        const form = document.getElementById("addToCartForm");
        const quantity = document.getElementById("quantity");
        const errorSpan = document.getElementById("quantityError");

        quantity.addEventListener("input", () => {
            errorSpan.textContent = "";
        });

        form.addEventListener("submit", function(e) {
            const val = quantity.value.trim();
            const re = /^[1-9]\d*$/;

            if (!re.test(val)) {
                e.preventDefault();
                errorSpan.textContent = "Devi inserire un intero positivo.";
                quantity.focus();
            }
        });
    });
    </script>

    <% } else { %>
       <p>Nessun dettaglio disponibile per questo accessorio.</p>
    <% } %>
</div>
</div>
<div class="footer">
    <%@ include file="Footer.jsp" %>
</div>
</body>
</html>
