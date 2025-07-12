<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.*, it.unisa.Model.OrdineBean" %>
<%@ include file="/Header.jsp" %>

<%
    @SuppressWarnings("unchecked")
    Collection<OrdineBean> ordini =
        (Collection<OrdineBean>) request.getAttribute("ordini");
    String emailParam = request.getParameter("email");
    String fromParam  = request.getParameter("from");
    String toParam    = request.getParameter("to");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ordini Amministratore</title>
    <!-- <link href="<%= request.getContextPath() %>/ProductStyle.css" 
          rel="stylesheet" type="text/css">-->
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
    <h2>Elenco Ordini (tutti gli utenti)</h2>

    <form id="filterOrdersForm"
          action="<%= request.getContextPath() %>/admin/orders"
          method="get"
          novalidate>
        <label for="email">Email utente:</label>
        <input type="text"
               id="email"
               name="email"
               value="<%= emailParam != null ? emailParam : "" %>"
               placeholder="esempio@dominio.com">
        <span id="emailError" class="error"></span><br><br>

        <label for="fromDate">Da:</label>
        <input type="date"
               id="fromDate"
               name="from"
               value="<%= fromParam != null ? fromParam : "" %>"
               placeholder="yyyy-MM-dd">
        <span id="fromError" class="error"></span>

        <label for="toDate">A:</label>
        <input type="date"
               id="toDate"
               name="to"
               value="<%= toParam != null ? toParam : "" %>"
               placeholder="yyyy-MM-dd">
        <span id="toError" class="error"></span><br><br>

        <input type="submit" value="Filtra">
    </form>

    <table border="1">
        <tr>
            <th>ID</th>
            <th>Utente</th>
            <th>Data</th>
            <th>Totale</th>
            <th>Azione</th>
        </tr>
        <%
            if (ordini != null && !ordini.isEmpty()) {
                for (OrdineBean o : ordini) {
        %>
        <tr>
            <td><%= o.getId() %></td>
            <td><%= o.getEmailUtente() %></td>
            <td><%= o.getDataOrdine() %></td>
            <td><%= String.format("%.2f €", o.getTotaleFattura()) %></td>
            <td>
                <a href="<%= request.getContextPath() %>/OrdineControl?action=read&id=<%= o.getId() %>">
                    Dettagli
                </a>
            </td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="5" style="text-align:center">
                Nessun ordine trovato.
            </td>
        </tr>
        <%
            }
        %>
    </table>

    <%@ include file="/Footer.jsp" %>

    <script>
    document.addEventListener('DOMContentLoaded', function() {
      var form   = document.getElementById('filterOrdersForm');
      var email  = document.getElementById('email');
      var from   = document.getElementById('fromDate');
      var to     = document.getElementById('toDate');

      var emailError = document.getElementById('emailError');
      var fromError  = document.getElementById('fromError');
      var toError    = document.getElementById('toError');

      [email, from, to].forEach(function(f) {
        f.addEventListener('input', function() {
          document.getElementById(f.id + 'Error').textContent = '';
        });
      });

      form.addEventListener('submit', function(e) {
        var emailVal = email.value.trim();
        if (emailVal) {
          var emailRe = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
          if (!emailRe.test(emailVal)) {
            e.preventDefault();
            emailError.textContent = 'Email non valida';
            email.focus();
            return;
          }
        }

        var fromVal = from.value.trim();
        if (fromVal) {
          var dateRe = /^\d{4}-\d{2}-\d{2}$/;
          if (!dateRe.test(fromVal)) {
            e.preventDefault();
            fromError.textContent = 'Data non valida';
            from.focus();
            return;
          }
        }

        var toVal = to.value.trim();
        if (toVal) {
          var dateRe = /^\d{4}-\d{2}-\d{2}$/;
          if (!dateRe.test(toVal)) {
            e.preventDefault();
            toError.textContent = 'Data non valida';
            to.focus();
            return;
          }
        }

        if (fromVal && toVal) {
          var dFrom = new Date(fromVal);
          var dTo   = new Date(toVal);
          if (dFrom > dTo) {
            e.preventDefault();
            toError.textContent = 'La data "A" deve essere ≥ data "Da"';
            to.focus();
            return;
          }
        }
      });
    });
    </script>
</body>
</html>
