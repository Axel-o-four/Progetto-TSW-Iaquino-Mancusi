<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="it.unisa.Cart" %>
<%@ page import="it.unisa.Item" %>
<%@ include file="Header.jsp" %>

<%
    Cart cart = (Cart) session.getAttribute("cart");
    if(cart == null) {
        cart = new Cart();
        session.setAttribute("cart", cart);
    }
    int totalQuantity = cart.getTotalQuantity();
    double totalPrice = cart.getTotalPrice();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Checkout - Pixel Emporium</title>
    <link rel="stylesheet" type="text/css" href="ProductStyle.css">
</head>
<body>
    <h2>Checkout</h2>
    
    <h3>Riepilogo Carrello</h3>
    <p>Totale articoli: <strong><%= totalQuantity %></strong></p>
    <p>Importo totale: <strong><%= totalPrice %>€</strong></p>
    
    <form action="OrdineControl" method="post">
        <input type="hidden" name="action" value="insert">
        
        <fieldset>
            <legend>Dati Utente e Spedizione</legend>
            
            <label for="emailUtente">Email Utente:</label>
            <input type="email" id="emailUtente" name="emailUtente" required><br><br>
            
            <label for="paese">Paese:</label>
            <input type="text" id="paese" name="paese" required><br><br>
            
            <label for="citta">Città:</label>
            <input type="text" id="citta" name="citta" required><br><br>
            
            <label for="cap">CAP:</label>
            <input type="text" id="cap" name="cap" maxlength="5" required><br><br>
            
            <label for="provincia">Provincia:</label>
            <input type="text" id="provincia" name="provincia" maxlength="2" required><br><br>
            
            <label for="via">Via:</label>
            <input type="text" id="via" name="via" required><br><br>
            
            <label for="numeroCivico">Numero Civico:</label>
            <input type="text" id="numeroCivico" name="numeroCivico" required><br><br>
        </fieldset>
        
        <fieldset>
            <legend>Dati di Pagamento</legend>
            
            <label for="tipoPagamento">Metodo di pagamento:</label>
            <select id="tipoPagamento" name="tipoPagamento" required>
                <option value="C">Carta</option>
                <option value="P">PayPal</option>
            </select><br><br>
            
            <div id="cardDetails">
                <label for="numeroCarta">Numero Carta:</label>
                <input type="text" id="numeroCarta" name="numeroCarta" maxlength="16"><br><br>
                
                <label for="scadenzaCarta">Scadenza Carta:</label>
                <input type="date" id="scadenzaCarta" name="scadenzaCarta"><br><br>
                
                <label for="cvv">CVV:</label>
                <input type="text" id="cvv" name="cvv" maxlength="3"><br><br>
            </div>
            
            <div id="paypalDetails">
                <label for="emailPaypal">Email PayPal:</label>
                <input type="email" id="emailPaypal" name="emailPaypal"><br><br>
            </div>
        </fieldset>
        
        <input type="submit" value="Conferma Acquisto">
    </form>
    
    <%@ include file="Footer.jsp" %>
    
    <script>
        var tipoPagamentoSelect = document.getElementById("tipoPagamento");
        var cardDetails = document.getElementById("cardDetails");
        var paypalDetails = document.getElementById("paypalDetails");
        
        function updatePaymentFields() {
            if (tipoPagamentoSelect.value === "C") {
                cardDetails.style.display = "block";
                paypalDetails.style.display = "none";
            } else if (tipoPagamentoSelect.value === "P") {
                cardDetails.style.display = "none";
                paypalDetails.style.display = "block";
            }
        }
        
        tipoPagamentoSelect.addEventListener("change", updatePaymentFields);
        updatePaymentFields();
    </script>
</body>
</html>
