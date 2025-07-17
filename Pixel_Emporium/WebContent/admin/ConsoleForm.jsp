<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="it.unisa.Model.ConsoleBean" %>
<%
  ConsoleBean c      = (ConsoleBean) request.getAttribute("console");
  boolean   isEdit   = (c != null);
  int       currentYear = java.time.Year.now().getValue();
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title><%= isEdit ? "Modifica Console" : "Inserisci Console" %></title>
  <link href="<%= request.getContextPath() %>/css/form.css" rel="stylesheet" type="text/css">
  <link rel="icon" type="image/png" href="<%=request.getContextPath()%>/media/icon.png">
</head>
<body>
<div class="page">
<div class="header">
	<%@ include file="/Header.jsp" %>
</div>
<div class="body">
  <h1><%= isEdit ? "Modifica Console" : "Inserisci Console" %></h1>
  <form id="consoleForm"
        action="<%= request.getContextPath() %>/ConsoleControl"
        method="post"
        enctype="multipart/form-data"
        novalidate>
    <input type="hidden" name="action" value="<%= isEdit ? "update" : "insert" %>"/>
    <% if (isEdit) { %>
      <input type="hidden" name="id"           value="<%= c.getCode() %>"/>
      <input type="hidden" name="currentImage" value="<%= c.getImage() %>"/>
    <% } else { %>
      <input type="hidden" name="prefissoId" value="C"/>
    <% } %>

    <label for="name">Nome:</label><br>
    <input type="text"
           id="name"
           name="name"
           required
           placeholder="2–100 caratteri (lettere, numeri e spazi)"
           value="<%= isEdit ? c.getName() : "" %>"/>
    <span id="nameError" class="error"></span><br><br>

    <label for="description">Descrizione:</label><br>
    <textarea id="description"
              name="description"
              rows="4"
              required
              placeholder="Minimo 5 caratteri"><%= isEdit ? c.getDescription() : "" %></textarea>
    <span id="descriptionError" class="error"></span><br><br>

    <label for="image">Immagine:</label><br>
    <input type="file"
           id="image"
           name="image"
           accept="image/*"
           <%= isEdit ? "" : "required" %> />
    <span id="imageError" class="error"></span><br>
    <% if (isEdit) { %>
      <small>Immagine attuale: <%= c.getImage() %></small><br><br>
    <% } else { %>
      <br>
    <% } %>

    <label for="brand">Marchio:</label><br>
    <input type="text"
           id="brand"
           name="brand"
           required
           placeholder="Es. Sony, Logitech"
           value="<%= isEdit ? c.getBrand() : "" %>"/>
    <span id="brandError" class="error"></span><br><br>

    <label for="price">Prezzo (€):</label><br>
    <input type="number"
           id="price"
           name="price"
           required
           step="0.01"
           min="0"
           placeholder="Formato 0.00"
           value="<%= isEdit ? c.getPrice() : "" %>"/>
    <span id="priceError" class="error"></span><br><br>

    <label for="releaseYear">Anno di rilascio:</label><br>
    <input type="number"
           id="releaseYear"
           name="releaseYear"
           required
           min="1980"
           max="<%= currentYear %>"
           placeholder="1980–<%= currentYear %>"
           value="<%= isEdit ? c.getReleaseYear() : "" %>"/>
    <span id="releaseYearError" class="error"></span><br><br>

    <label for="support">Supporti:</label><br>
    <select id="support" name="support" required>
      <option value="">Seleziona...</option>
      <option value="DISCHI"       <%= isEdit && "DISCHI".equals(c.getSupport())       ? "selected" : "" %>>DISCHI</option>
      <option value="CARTUCCIA"    <%= isEdit && "CARTUCCIA".equals(c.getSupport())    ? "selected" : "" %>>CARTUCCIA</option>
      <option value="SOLO DIGITALE"<%= isEdit && "SOLO DIGITALE".equals(c.getSupport())? "selected" : "" %>>SOLO DIGITALE</option>
    </select>
    <span id="supportError" class="error"></span><br><br>

    <label for="retroCompatibility">Retrocompatibilità:</label><br>
    <select id="retroCompatibility" name="retroCompatibility" required>
      <option value="">Seleziona...</option>
      <option value="true"  <%= isEdit && c.isRetroCompatibility()  ? "selected" : "" %>>Sì</option>
      <option value="false" <%= isEdit && !c.isRetroCompatibility() ? "selected" : "" %>>No</option>
    </select>
    <span id="retroCompatibilityError" class="error"></span><br><br>

    <label for="storage">Archiviazione:</label><br>
    <input type="text"
           id="storage"
           name="storage"
           placeholder="Es. 500GB, 1TB"
           value="<%= isEdit ? c.getStorage() : "" %>"/>
    <span id="storageError" class="error"></span><br><br>

    <label for="generation">Generazione:</label><br>
    <input type="number"
           id="generation"
           name="generation"
           min="1"
           placeholder="Numero intero"
           value="<%= isEdit ? c.getGeneration() : "" %>"/>
    <span id="generationError" class="error"></span><br><br>

    <label for="quantity">Quantità:</label><br>
    <input type="number"
           id="quantity"
           name="quantity"
           required
           min="1"
           placeholder="Intero positivo"
           value="<%= isEdit ? c.getQuantity() : "1" %>"/>
    <span id="quantityError" class="error"></span><br><br>

    <input type="submit" value="<%= isEdit ? "Aggiorna" : "Inserisci" %>"/>
  </form>


  <script>
  document.addEventListener('DOMContentLoaded', function() {
    var form    = document.getElementById('consoleForm');
    var nameFld = document.getElementById('name');
    var descFld = document.getElementById('description');
    var imgFld  = document.getElementById('image');
    var brandFld= document.getElementById('brand');
    var priceFld= document.getElementById('price');
    var yearFld = document.getElementById('releaseYear');
    var supFld  = document.getElementById('support');
    var retroFld= document.getElementById('retroCompatibility');
    var storFld = document.getElementById('storage');
    var genFld  = document.getElementById('generation');
    var qtyFld  = document.getElementById('quantity');

    function showError(fld, msg) {
      document.getElementById(fld.id + 'Error').textContent = msg;
      fld.focus();
    }
    function clearError(fld) {
      document.getElementById(fld.id + 'Error').textContent = '';
    }

    [nameFld, descFld, imgFld, brandFld,
     priceFld, yearFld, supFld, retroFld,
     storFld, genFld, qtyFld].forEach(function(f) {
      if (f) f.addEventListener('input', function() {
        clearError(f);
      });
    });

    form.addEventListener('submit', function(e) {
      if (!/^[\w ]{2,100}$/.test(nameFld.value.trim())) {
        e.preventDefault();
        showError(nameFld, 'Nome non valido (2–100 caratteri)');
        return;
      }
      if (descFld.value.trim().length < 5) {
        e.preventDefault();
        showError(descFld, 'Descrizione troppo breve');
        return;
      }
      if (imgFld.required && imgFld.files.length === 0) {
        e.preventDefault();
        showError(imgFld, 'Seleziona un file immagine');
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
      var yr = parseInt(yearFld.value, 10);
      if (isNaN(yr) || yr < 1980 || yr > <%= currentYear %>) {
        e.preventDefault();
        showError(yearFld, 'Anno fuori intervallo');
        return;
      }
      if (!supFld.value) {
        e.preventDefault();
        showError(supFld, 'Seleziona supporto');
        return;
      }
      if (!retroFld.value) {
        e.preventDefault();
        showError(retroFld, 'Seleziona retrocompatibilità');
        return;
      }
      var s = storFld.value.trim();
      if (s && (s.length < 2 || s.length > 30)) {
        e.preventDefault();
        showError(storFld, 'Archiviazione non valida');
        return;
      }
      if (genFld.value && !/^[1-9]\d*$/.test(genFld.value.trim())) {
        e.preventDefault();
        showError(genFld, 'Deve essere intero positivo');
        return;
      }
      if (!/^[1-9]\d*$/.test(qtyFld.value.trim())) {
        e.preventDefault();
        showError(qtyFld, 'Intero positivo obbligatorio');
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

