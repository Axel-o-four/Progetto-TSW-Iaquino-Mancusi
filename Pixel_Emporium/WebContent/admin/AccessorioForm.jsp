<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="it.unisa.Model.AccessorioBean" %>
<%
  AccessorioBean a     = (AccessorioBean) request.getAttribute("accessorio");
  boolean        edit  = (a != null);
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title><%= edit ? "Modifica Accessorio" : "Inserisci Accessorio" %></title>
  <link href="<%= request.getContextPath() %>/css/form.css" rel="stylesheet" type="text/css">
  <link rel="icon" type="image/png" href="<%=request.getContextPath()%>/media/icon.png">
</head>
<body>
<div class="page">
<div class="header">
	<%@ include file="/Header.jsp" %>
</div>
<div class="body">
  <h1><%= edit ? "Modifica Accessorio" : "Inserisci Accessorio" %></h1>
  <form id="accessorioForm"
        action="<%= request.getContextPath() %>/AccessorioControl"
        method="post"
        enctype="multipart/form-data"
        novalidate>
    <input type="hidden" name="action" value="<%= edit ? "update" : "insert" %>"/>
    <% if (edit) { %>
      <input type="hidden" name="id"           value="<%= a.getCode() %>"/>
      <input type="hidden" name="currentImage" value="<%= a.getImage() %>"/>
    <% } else { %>
      <input type="hidden" name="prefissoId" value="A"/>
    <% } %>

    <label for="name">Nome:</label><br>
    <input type="text"
           id="name"
           name="name"
           required
           placeholder="Inserisci nome accessorio"
           value="<%= edit ? a.getName() : "" %>"/>
    <span id="nameError" class="error"></span><br><br>

    <label for="description">Descrizione:</label><br>
    <textarea id="description"
              name="description"
              rows="3"
              required
              placeholder="Descrivi brevemente l'accessorio"><%= edit ? a.getDescription() : "" %></textarea>
    <span id="descriptionError" class="error"></span><br><br>

    <label for="image">Immagine:</label><br>
    <input type="file"
           id="image"
           name="image"
           accept="image/*"
           <%= edit ? "" : "required" %>/>
    <span id="imageError" class="error"></span><br>
    <% if (edit) { %>
      <small>Attuale: <%= a.getImage() %></small><br><br>
    <% } else { %>
      <br>
    <% } %>

    <label for="brand">Marchio:</label><br>
    <input type="text"
           id="brand"
           name="brand"
           required
           placeholder="Es. Sony, Logitech"
           value="<%= edit ? a.getBrand() : "" %>"/>
    <span id="brandError" class="error"></span><br><br>

    <label for="price">Prezzo (€):</label><br>
    <input type="number"
           id="price"
           name="price"
           required
           step="0.01"
           min="0"
           placeholder="Es. 19.99"
           value="<%= edit ? String.format("%.2f", a.getPrice()).replace(",", ".") : "" %>"/>
    <span id="priceError" class="error"></span><br><br>

    <label for="accessoryType">Tipo Accessorio:</label><br>
    <input type="text"
           id="accessoryType"
           name="accessoryType"
           required
           placeholder="Es. Cavo, Custodia"
           value="<%= edit ? a.getAccessoryType() : "" %>"/>
    <span id="typeError" class="error"></span><br><br>

    <label for="quantity">Quantità:</label><br>
    <input type="number"
           id="quantity"
           name="quantity"
           required
           min="1"
           placeholder="Intero positivo"
           value="<%= edit ? a.getQuantity() : "1" %>"/>
    <span id="qtyError" class="error"></span><br><br>

    <input type="submit" value="<%= edit ? "Aggiorna" : "Inserisci" %>"/>
  </form>


  <script>
    document.addEventListener('DOMContentLoaded', function() {
      var form        = document.getElementById('accessorioForm');
      var nameFld     = document.getElementById('name');
      var descFld     = document.getElementById('description');
      var imageFld    = document.getElementById('image');
      var brandFld    = document.getElementById('brand');
      var priceFld    = document.getElementById('price');
      var typeFld     = document.getElementById('accessoryType');
      var qtyFld      = document.getElementById('quantity');

      function showError(input, msg) {
        document.getElementById(input.id + 'Error').textContent = msg;
        input.focus();
      }
      function clearError(input) {
        document.getElementById(input.id + 'Error').textContent = '';
      }

      [nameFld, descFld, imageFld, brandFld, priceFld, typeFld, qtyFld]
        .forEach(function(f) {
          if (f) f.addEventListener('input', function() {
            clearError(f);
          });
        });

      form.addEventListener('submit', function(e) {
        if (!/^[\w ]{2,100}$/.test(nameFld.value.trim())) {
          e.preventDefault();
          showError(nameFld, 'Nome non valido');
          return;
        }
        if (descFld.value.trim().length < 5) {
          e.preventDefault();
          showError(descFld, 'Descrizione troppo breve');
          return;
        }
        if (imageFld.required && imageFld.files.length === 0) {
          e.preventDefault();
          showError(imageFld, 'Seleziona un file immagine');
          return;
        }
        if (!/^[\w ]{2,50}$/.test(brandFld.value.trim())) {
          e.preventDefault();
          showError(brandFld, 'Marchio non valido');
          return;
        }
        if (!/^\d+(\.\d{1,2})?$/.test(priceFld.value.trim())) {
          e.preventDefault();
          showError(priceFld, 'Formato prezzo errato');
          return;
        }
        if (!/^[\w ]{2,50}$/.test(typeFld.value.trim())) {
          e.preventDefault();
          showError(typeFld, 'Tipo non valido');
          return;
        }
        if (!/^[1-9]\d*$/.test(qtyFld.value.trim())) {
          e.preventDefault();
          showError(qtyFld, 'Inserisci un intero positivo');
          return;
        }
      });
    });
  </script>
  </div>
  </div>
  <div class="footer">
  	  <%@ include file="/Footer.jsp" %>
  </div>
</body>
</html>
