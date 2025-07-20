<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="it.unisa.Model.Cart,it.unisa.Model.UserBean" %>

<%
    Cart c = (Cart) session.getAttribute("cart");
    if (c == null || c.getTotalQuantity() == 0) {
        response.sendRedirect("CartView.jsp");
        return;
    }
%>

<%
    Cart cart = (Cart) session.getAttribute("cart");
    if (cart == null) {
        cart = new Cart();
        session.setAttribute("cart", cart);
    }
    int totalQuantity = cart.getTotalQuantity();
    double totalPrice = cart.getTotalPrice();

    UserBean user = (UserBean) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    String emailUtente = user.getEmail();
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Checkout - Pixel Emporium</title>
  <link rel="icon" type="image/png" href="<%=request.getContextPath()%>/media/icon.png">
  <link href="css/checkout.css" type="text/css" rel="stylesheet">
</head>
<body>
<div class="page">
<div class="header">
	<%@ include file="Header.jsp" %>
</div>
<div class="body">
  <h1>Checkout</h1>

  <h3>Riepilogo Carrello</h3>
  <p>Totale articoli: <strong><%= totalQuantity %></strong></p>
  <p>Importo totale: <strong><%= String.format("%.2f", totalPrice) %>€</strong></p>

  <form id="checkoutForm" action="OrdineControl" method="post" novalidate>
    <input type="hidden" name="action" value="insert">
    <input type="hidden" name="emailUtente" value="<%= emailUtente %>">

    <fieldset>
      <legend>Dati Utente e Spedizione</legend>
      <p><strong>Email Utente:</strong> <%= emailUtente %></p>

      <label for="paese">Paese:</label>
      <input type="text" id="paese" name="paese" placeholder="Solo lettere e spazi">
      <span class="error" id="paeseError"></span><br><br>

      <label for="citta">Città:</label>
      <input type="text" id="citta" name="citta" placeholder="Solo lettere e spazi">
      <span class="error" id="cittaError"></span><br><br>

      <label for="cap">CAP:</label>
      <input type="text" id="cap" name="cap" maxlength="5" placeholder="5 cifre">
      <span class="error" id="capError"></span><br><br>

      <label for="provincia">Provincia:</label>
      <input type="text" id="provincia" name="provincia" maxlength="2" placeholder="2 lettere maiuscole">
      <span class="error" id="provinciaError"></span><br><br>

      <label for="via">Via:</label>
      <input type="text" id="via" name="via" placeholder="Nome della via">
      <span class="error" id="viaError"></span><br><br>

      <label for="numeroCivico">Numero Civico:</label>
      <input type="text" id="numeroCivico" name="numeroCivico" placeholder="Es. 12B">
      <span class="error" id="numeroCivicoError"></span><br><br>
    </fieldset>

    <fieldset>
      <legend>Dati di Pagamento</legend>
      <label for="tipoPagamento">Metodo di pagamento:</label>
      <select id="tipoPagamento" name="tipoPagamento">
        <option value="C">Carta</option>
        <option value="P">PayPal</option>
      </select>
      <span class="error" id="tipoPagamentoError"></span><br><br>

      <div id="cardDetails">
        <label for="numeroCarta">Numero Carta:</label>
        <input type="text" id="numeroCarta" name="numeroCarta" maxlength="16" placeholder="16 cifre">
        <span class="error" id="numeroCartaError"></span><br><br>

        <label for="scadenzaCarta">Scadenza Carta:</label>
        <input type="text" id="scadenzaCarta" name="scadenzaCarta" placeholder="MM/YYYY" pattern="(0[1-9]|1[0-2])\/\d{4}">
        <span class="error" id="scadenzaCartaError"></span><br><br>

        <label for="cvv">CVV:</label>
        <input type="text" id="cvv" name="cvv" maxlength="3" placeholder="3 cifre">
        <span class="error" id="cvvError"></span><br><br>
      </div>

      <div id="paypalDetails">
        <label for="emailPaypal">Email PayPal:</label>
        <input type="email" id="emailPaypal" name="emailPaypal" placeholder="esempio@dominio.com">
        <span class="error" id="emailPaypalError"></span><br><br>
      </div>
    </fieldset>

    <input type="submit" value="Conferma Acquisto">
  </form>


  <script>
    document.addEventListener('DOMContentLoaded', function() {
      var form = document.getElementById('checkoutForm');
      var tipoPagamento = document.getElementById('tipoPagamento');
      var cardDetails = document.getElementById('cardDetails');
      var paypalDetails = document.getElementById('paypalDetails');
      var testMonth = document.createElement('input');

      function aggiornaMetodi() {
        if (tipoPagamento.value === 'C') {
          cardDetails.style.display = 'block';
          paypalDetails.style.display = 'none';
        } else {
          cardDetails.style.display = 'none';
          paypalDetails.style.display = 'block';
        }
      }
      tipoPagamento.addEventListener('change', aggiornaMetodi);
      aggiornaMetodi();

      form.addEventListener('submit', function(e) {
        var paese = document.getElementById('paese');
        var paeseError = document.getElementById('paeseError');
        if (!/^[A-Za-z ]+$/.test(paese.value.trim())) {
          paeseError.textContent = 'Solo lettere e spazi';
          paese.focus();
          e.preventDefault();
          return;
        } else {
          paeseError.textContent = '';
        }

        var citta = document.getElementById('citta');
        var cittaError = document.getElementById('cittaError');
        if (!/^[A-Za-z ]+$/.test(citta.value.trim())) {
          cittaError.textContent = 'Solo lettere e spazi';
          citta.focus();
          e.preventDefault();
          return;
        } else {
          cittaError.textContent = '';
        }

        var cap = document.getElementById('cap');
        var capError = document.getElementById('capError');
        if (!/^\d{5}$/.test(cap.value.trim())) {
          capError.textContent = 'Deve essere 5 cifre';
          cap.focus();
          e.preventDefault();
          return;
        } else {
          capError.textContent = '';
        }

        var provincia = document.getElementById('provincia');
        var provinciaError = document.getElementById('provinciaError');
        if (!/^[A-Z]{2}$/.test(provincia.value.trim())) {
          provinciaError.textContent = '2 lettere maiuscole';
          provincia.focus();
          e.preventDefault();
          return;
        } else {
          provinciaError.textContent = '';
        }

        var via = document.getElementById('via');
        var viaError = document.getElementById('viaError');
        if (via.value.trim() === '') {
          viaError.textContent = 'Obbligatorio';
          via.focus();
          e.preventDefault();
          return;
        } else {
          viaError.textContent = '';
        }

        var numeroCivico = document.getElementById('numeroCivico');
        var numeroCivicoError = document.getElementById('numeroCivicoError');
        if (!/^\d+[A-Za-z]?$/.test(numeroCivico.value.trim())) {
          numeroCivicoError.textContent = 'Formato es. 12B';
          numeroCivico.focus();
          e.preventDefault();
          return;
        } else {
          numeroCivicoError.textContent = '';
        }

        var metodoError = document.getElementById('tipoPagamentoError');
        if (tipoPagamento.value !== 'C' && tipoPagamento.value !== 'P') {
          metodoError.textContent = 'Seleziona metodo';
          tipoPagamento.focus();
          e.preventDefault();
          return;
        } else {
          metodoError.textContent = '';
        }

        if (tipoPagamento.value === 'C') {
          var numeroCarta = document.getElementById('numeroCarta');
          var numeroCartaError = document.getElementById('numeroCartaError');
          if (!/^\d{16}$/.test(numeroCarta.value.trim())) {
            numeroCartaError.textContent = '16 cifre';
            numeroCarta.focus();
            e.preventDefault();
            return;
          } else {
            numeroCartaError.textContent = '';
          }

          var scadenzaCarta      = document.getElementById('scadenzaCarta');
          var scadenzaCartaError = document.getElementById('scadenzaCartaError');
          var sc = scadenzaCarta.value.trim();
          if (!/^(0[1-9]|1[0-2])\/\d{4}$/.test(sc)) {
            scadenzaCartaError.textContent = 'Formato MM/YYYY';
            scadenzaCarta.focus();
            e.preventDefault();
            return;
          } else {
            scadenzaCartaError.textContent = '';
          }

          var cvv = document.getElementById('cvv');
          var cvvError = document.getElementById('cvvError');
          if (!/^\d{3}$/.test(cvv.value.trim())) {
            cvvError.textContent = '3 cifre';
            cvv.focus();
            e.preventDefault();
            return;
          } else {
            cvvError.textContent = '';
          }
        }
        else {
          var emailPaypal = document.getElementById('emailPaypal');
          var emailPaypalError = document.getElementById('emailPaypalError');
          if (emailPaypal.value.trim() === '' || !emailPaypal.checkValidity()) {
            emailPaypalError.textContent = 'Email non valida';
            emailPaypal.focus();
            e.preventDefault();
            return;
          } else {
            emailPaypalError.textContent = '';
          }
        }
      });
    });
  </script>
  </div>
  </div>
  <div class="footer">
  	  <%@ include file="Footer.jsp" %>
  </div>
</body>
</html>
