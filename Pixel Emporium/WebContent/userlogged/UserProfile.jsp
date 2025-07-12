<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.Model.UserBean, java.util.List" %>
<%@ include file="/Header.jsp" %>
<%
    UserBean user = (UserBean) session.getAttribute("user");
    List<String> errorMessages = (List<String>) request.getAttribute("errorMessages");
    String message = (String) request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Profilo Utente</title>
    <link href="ProductStyle.css" rel="stylesheet" type="text/css">
    <style>
      .error {
        color: #c00;
        font-size: 0.9em;
        margin-left: 5px;
      }
      input:focus, select:focus {
        outline: 2px solid #06f;
      }
    </style>
</head>
<body>
    <h2>Profilo Utente</h2>
    <% if (errorMessages != null && !errorMessages.isEmpty()) { %>
      <div style="color:red;">
        <ul>
        <% for (String err : errorMessages) { %>
          <li><%= err %></li>
        <% } %>
        </ul>
      </div>
    <% } %>
    <% if (message != null) { %>
      <p style="color:green;"><%= message %></p>
    <% } %>

    <form id="profileForm"
          action="<%= request.getContextPath() %>/ProfileControl"
          method="post"
          novalidate>
      <table>
        <tr>
          <td>Email:</td>
          <td>
            <input type="text"
                   id="email"
                   name="email"
                   value="<%= user.getEmail() %>"
                   readonly
                   placeholder="Non modificabile">
          </td>
        </tr>
        <tr>
          <td>Nome:</td>
          <td>
            <input type="text"
                   id="nome"
                   name="nome"
                   value="<%= user.getNome() %>"
                   required
                   placeholder="Solo lettere">
            <span id="nomeError" class="error"></span>
          </td>
        </tr>
        <tr>
          <td>Cognome:</td>
          <td>
            <input type="text"
                   id="cognome"
                   name="cognome"
                   value="<%= user.getCognome() %>"
                   required
                   placeholder="Solo lettere">
            <span id="cognomeError" class="error"></span>
          </td>
        </tr>
        <tr>
          <td>Data di nascita:</td>
          <td>
            <input type="date"
                   id="dataNascita"
                   name="dataNascita"
                   value="<%= user.getDataNascita() != null
                             ? user.getDataNascita().toString()
                             : "" %>"
                   required>
            <span id="dataNascitaError" class="error"></span>
          </td>
        </tr>
        <tr>
          <td>Genere:</td>
          <td>
            <select id="genere" name="genere">
              <option value="M"
                <%= "M".equals(user.getGenere()) ? "selected" : "" %>>
                Maschile
              </option>
              <option value="F"
                <%= "F".equals(user.getGenere()) ? "selected" : "" %>>
                Femminile
              </option>
              <option value="O"
                <%= "O".equals(user.getGenere()) ? "selected" : "" %>>
                Altro
              </option>
            </select>
            <span id="genereError" class="error"></span>
          </td>
        </tr>
        <tr>
          <td>Indirizzo:</td>
          <td>
            <input type="text"
                   id="indirizzo"
                   name="indirizzo"
                   value="<%= user.getIndirizzo() %>"
                   required
                   placeholder="Via e numero civico">
            <span id="indirizzoError" class="error"></span>
          </td>
        </tr>
        <tr>
          <td>Città:</td>
          <td>
            <input type="text"
                   id="citta"
                   name="citta"
                   value="<%= user.getCitta() %>"
                   required
                   placeholder="Solo lettere e spazi">
            <span id="cittaError" class="error"></span>
          </td>
        </tr>
        <tr>
          <td>Provincia:</td>
          <td>
            <input type="text"
                   id="prov"
                   name="prov"
                   value="<%= user.getProv() %>"
                   required
                   maxlength="2"
                   placeholder="2 lettere maiuscole">
            <span id="provError" class="error"></span>
          </td>
        </tr>
        <tr>
          <td>CAP:</td>
          <td>
            <input type="text"
                   id="cap"
                   name="cap"
                   value="<%= user.getCap() %>"
                   required
                   maxlength="5"
                   placeholder="5 cifre">
            <span id="capError" class="error"></span>
          </td>
        </tr>
        <tr>
          <td>Nuova password:</td>
          <td>
            <input type="password"
                   id="password"
                   name="password"
                   placeholder="Vuoto per non cambiare">
            <span id="passwordError" class="error"></span>
          </td>
        </tr>
        <tr>
          <td colspan="2">
            <input type="submit" value="Aggiorna Profilo">
          </td>
        </tr>
      </table>
    </form>

    <%@ include file="/Footer.jsp" %>

    <script>
    document.addEventListener('DOMContentLoaded', function() {
      var form      = document.getElementById('profileForm');
      var nome      = document.getElementById('nome');
      var cognome   = document.getElementById('cognome');
      var dataN     = document.getElementById('dataNascita');
      var genere    = document.getElementById('genere');
      var indirizzo = document.getElementById('indirizzo');
      var citta     = document.getElementById('citta');
      var prov      = document.getElementById('prov');
      var cap       = document.getElementById('cap');
      var pwd       = document.getElementById('password');

      function setError(field, msg) {
        document.getElementById(field.id + 'Error').textContent = msg;
        field.focus();
      }
      function clearError(field) {
        document.getElementById(field.id + 'Error').textContent = '';
      }

      [nome,cognome,dataN,genere,indirizzo,citta,prov,cap,pwd]
        .forEach(function(f) {
          f.addEventListener('input', function() {
            clearError(f);
          });
        });

      form.addEventListener('submit', function(e) {
        if (!/^[A-Za-z]+$/.test(nome.value.trim())) {
          e.preventDefault();
          setError(nome, 'Solo lettere');
          return;
        }
        if (!/^[A-Za-z]+$/.test(cognome.value.trim())) {
          e.preventDefault();
          setError(cognome, 'Solo lettere');
          return;
        }
        var today = new Date().toISOString().slice(0,10);
        if (!/^\d{4}-\d{2}-\d{2}$/.test(dataN.value) ||
            dataN.value > today) {
          e.preventDefault();
          setError(dataN, 'Data non valida');
          return;
        }
        if (!['M','F','O'].includes(genere.value)) {
          e.preventDefault();
          setError(genere, 'Seleziona genere');
          return;
        }
        if (indirizzo.value.trim() === '') {
          e.preventDefault();
          setError(indirizzo, 'Obbligatorio');
          return;
        }
        if (!/^[A-Za-z ]+$/.test(citta.value.trim())) {
          e.preventDefault();
          setError(citta, 'Solo lettere e spazi');
          return;
        }
        if (!/^[A-Z]{2}$/.test(prov.value.trim())) {
          e.preventDefault();
          setError(prov, '2 lettere maiuscole');
          return;
        }
        if (!/^\d{5}$/.test(cap.value.trim())) {
          e.preventDefault();
          setError(cap, '5 cifre');
          return;
        }
        if (pwd.value.trim() !== '' &&
            !/^(?=.*\d)(?=.*[A-Za-z]).{6,}$/.test(pwd.value)) {
          e.preventDefault();
          setError(pwd, 'Min 6 char. e 1 cifra');
          return;
        }
      });
    });
    </script>
</body>
</html>
