<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         import="
           java.util.List,
           java.sql.SQLException,
           it.unisa.Model.Cart,
           it.unisa.Model.Item,
           it.unisa.Model.GiocoModelDS,
           it.unisa.Model.ConsoleModelDS,
           it.unisa.Model.AccessorioModelDS,
           it.unisa.Model.GiocoBean,
           it.unisa.Model.ConsoleBean,
           it.unisa.Model.AccessorioBean
         " %>
<%@ include file="Header.jsp" %>

<%
    Cart cart = (Cart) session.getAttribute("cart");
    if (cart == null) {
        cart = new Cart();
        session.setAttribute("cart", cart);
    }
    List<Item> items = cart.getProducts();
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Carrello - Pixel Emporium</title>
  <link href="ProductStyle.css" rel="stylesheet" type="text/css">
  <style>
    input:focus { outline: 2px solid #06f; }
    .error { color: #c00; font-size: 0.9em; margin-left: 5px; }
    .focused { background: #eef; }
  </style>
</head>
<body>
  <h2>Carrello</h2>

  <% String cartError = (String) session.getAttribute("cartError");
     if (cartError != null) { %>
    <p class="error"><%= cartError %></p>
  <% session.removeAttribute("cartError"); } %>

  <% if (items == null || items.isEmpty()) { %>
    <p>Il carrello è vuoto.</p>
  <% } else { %>

    <table border="1">
      <tr>
        <th>Nome</th>
        <th>Quantità</th>
        <th>Azione</th>
      </tr>
      <% for (Item item : items) {
           int available = 0;
           String pre = item.getPrefissoId();
           try {
             if ("G".equals(pre)) {
               GiocoBean orig = new GiocoModelDS().doRetrieveByKey(item.getCode());
               available = orig!=null ? orig.getQuantity() : 0;
             } else if ("C".equals(pre)) {
               ConsoleBean orig = new ConsoleModelDS().doRetrieveByKey(item.getCode());
               available = orig!=null ? orig.getQuantity() : 0;
             } else if ("A".equals(pre)) {
               AccessorioBean orig = new AccessorioModelDS().doRetrieveByKey(item.getCode());
               available = orig!=null ? orig.getQuantity() : 0;
             }
           } catch (SQLException e) {
             available = 0;
           }
      %>
      <tr>
        <td><%= item.getName() %></td>
        <td>
          <form action="CartControl" method="get" class="qty-form" style="display:inline;">
            <input type="hidden" name="action"   value="updateC"/>
            <input type="hidden" name="id"       value="<%= item.getCode() %>"/>
            <input type="hidden" name="prefisso" value="<%= pre %>"/>

            <input
              name="quantity"
              type="number"
              min="1"
              max="<%= available %>"
              placeholder="1 – <%= available %>"
              value="<%= item.getQuantity() %>"
              class="qty-input"
              style="width:50px"
              required
            />
            <span class="error"></span>

            <input type="submit" value="Aggiorna"/>
          </form>
        </td>
        <td>
          <a href="CartControl?action=deleteC&id=<%= item.getCode() %>&prefisso=<%= pre %>">
            Elimina
          </a>
        </td>
      </tr>
      <% } %>
    </table>

    <hr/>
    <p>Totale articoli: <%= cart.getTotalQuantity() %></p>
    <p>Totale prezzo: <%= cart.getTotalPrice() %>€</p>

    <form action="Checkout.jsp" method="get">
      <input type="submit" value="Procedi al checkout">
    </form>

  <% } %>

  <%@ include file="Footer.jsp" %>

  <script>
    document.querySelectorAll('.qty-form').forEach(form => {
      const input = form.querySelector('input[name="quantity"]');
      const error = form.querySelector('.error');
      const regex = /^[1-9]\d*$/;
      input.addEventListener('focus', () => {
        input.classList.add('focused');
      });
      input.addEventListener('blur', () => {
        input.classList.remove('focused');
      });
      input.addEventListener('input', () => {
        error.textContent = '';
      });
      form.addEventListener('submit', e => {
        const val = input.value.trim();
        const max = parseInt(input.max, 10);
        let msg = '';

        if (!regex.test(val)) {
          msg = 'Inserisci un intero positivo.';
        } else if (+val < 1) {
          msg = 'Minimo 1.';
        } else if (+val > max) {
          msg = `Massimo ${max}.`;
        }

        if (msg) {
          e.preventDefault();
          error.textContent = msg;
          input.focus();
        }
      });
    });
  </script>
</body>
</html>
