<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisa.Model.GiocoBean" %>
<%
  GiocoBean gioco     = (GiocoBean) request.getAttribute("gioco");
  boolean  isEdit     = gioco != null;
  int      currentYear= java.time.Year.now().getValue();
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <link href="<%= request.getContextPath() %>/css/form.css" rel="stylesheet" type="text/css">
  <link rel="icon" type="image/png" href="<%=request.getContextPath()%>/media/icon.png">
  <title><%= isEdit ? "Modifica Videogioco" : "Inserisci Videogioco" %></title>
</head>
<body>
<div class="page">
<div class="header">
	<%@ include file="/Header.jsp" %>
</div>
<div class="body">
  <h1><%= isEdit ? "Modifica Videogioco" : "Inserisci Videogioco" %></h1>
  
  <form id="giocoForm"
        action="<%= request.getContextPath() %>/GiocoControl"
        method="post"
        enctype="multipart/form-data"
        novalidate>
    <input type="hidden" name="action" value="<%= isEdit ? "update" : "insert" %>"/>
    <% if (isEdit) { %>
      <input type="hidden" name="id"           value="<%= gioco.getCode() %>"/>
      <input type="hidden" name="currentImage" value="<%= gioco.getImage() %>"/>
    <% } else { %>
      <input type="hidden" name="prefissoId" value="G"/>
    <% } %>

    <label for="name">Nome:</label><br>
    <input
      type="text"
      id="name"
      name="name"
      required
      placeholder="2–100 caratteri: lettere, numeri e spazi"
      value="<%= isEdit ? gioco.getName() : "" %>"
    />
    <span id="nameError" class="error"></span><br><br>

    <label for="description">Descrizione:</label><br>
    <textarea
      id="description"
      name="description"
      rows="4"
      required
      placeholder="Minimo 5 caratteri"
    ><%= isEdit ? gioco.getDescription() : "" %></textarea>
    <span id="descriptionError" class="error"></span><br><br>

    <label for="image">Immagine:</label><br>
    <input
      type="file"
      id="image"
      name="image"
      accept="image/*"
      <%= isEdit ? "" : "required" %>
    />
    <span id="imageError" class="error"></span><br>
    <% if (isEdit) { %>
      <small>Immagine attuale: <%= gioco.getImage() %></small><br><br>
    <% } else { %>
      <br>
    <% } %>

    <label for="brand">Marchio:</label><br>
    <input
      type="text"
      id="brand"
      name="brand"
      required
      placeholder="Es. Nintendo, Ubisoft"
      value="<%= isEdit ? gioco.getBrand() : "" %>"
    />
    <span id="brandError" class="error"></span><br><br>

    <label for="price">Prezzo (€):</label><br>
    <input
      type="number"
      id="price"
      name="price"
      required
      step="0.01"
      min="0"
      placeholder="Formato 0.00"
      value='<%= isEdit ? String.format("%.2f", gioco.getPrice()).replace(",", ".") : "" %>'
    />
    <span id="priceError" class="error"></span><br><br>

    <label for="releaseYear">Anno di rilascio:</label><br>
    <input
      type="number"
      id="releaseYear"
      name="releaseYear"
      required
      min="1980"
      max="<%= currentYear %>"
      placeholder="1980–<%= currentYear %>"
      value="<%= isEdit ? gioco.getReleaseYear() : "" %>"
    />
    <span id="releaseYearError" class="error"></span><br><br>

    <label for="genre">Genere:</label><br>
    <input
      type="text"
      id="genre"
      name="genre"
      required
      placeholder="Solo lettere e spazi"
      value="<%= isEdit ? gioco.getGenre() : "" %>"
    />
    <span id="genreError" class="error"></span><br><br>

    <label for="pegi">PEGI:</label><br>
    <input
      type="text"
      id="pegi"
      name="pegi"
      required
      maxlength="2"
      placeholder="3–18"
      value="<%= isEdit ? gioco.getPegi() : "" %>"
    />
    <span id="pegiError" class="error"></span><br><br>

    <label for="format">Formato:</label><br>
    <select id="format" name="format" required>
      <option value="">Seleziona…</option>
      <option value="FISICO"   <%= isEdit && "FISICO".equals(gioco.getFormat())   ? "selected" : "" %>>FISICO</option>
      <option value="DIGITALE" <%= isEdit && "DIGITALE".equals(gioco.getFormat()) ? "selected" : "" %>>DIGITALE</option>
    </select>
    <span id="formatError" class="error"></span><br><br>

    <label for="quantity">Quantità:</label><br>
    <input
      type="number"
      id="quantity"
      name="quantity"
      required
      min="1"
      placeholder="Intero positivo"
      value="<%= isEdit ? gioco.getQuantity() : "1" %>"
    />
    <span id="quantityError" class="error"></span><br><br>

    <input type="submit" value="<%= isEdit ? "Aggiorna" : "Inserisci" %>"/>
  </form>


  <script>
  document.addEventListener('DOMContentLoaded', function() {
    var form        = document.getElementById('giocoForm');
    var nameFld     = document.getElementById('name');
    var descFld     = document.getElementById('description');
    var imgFld      = document.getElementById('image');
    var brandFld    = document.getElementById('brand');
    var priceFld    = document.getElementById('price');
    var yearFld     = document.getElementById('releaseYear');
    var genreFld    = document.getElementById('genre');
    var pegiFld     = document.getElementById('pegi');
    var formatFld   = document.getElementById('format');
    var qtyFld      = document.getElementById('quantity');

    function showError(fld, msg) {
      document.getElementById(fld.id + 'Error').textContent = msg;
      fld.focus();
    }
    function clearError(fld) {
      document.getElementById(fld.id + 'Error').textContent = '';
    }

    [nameFld, descFld, imgFld, brandFld, priceFld,
     yearFld, genreFld, pegiFld, formatFld, qtyFld]
      .forEach(function(f) {
        if (f) f.addEventListener('input', function() {
          clearError(f);
        });
      });

    form.addEventListener('submit', function(e) {
      if (!/^[\w ]{2,100}$/.test(nameFld.value.trim())) {
        e.preventDefault();
        showError(nameFld, '2–100 caratteri validi');
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
      if (!/^[A-Za-z ]+$/.test(genreFld.value.trim())) {
        e.preventDefault();
        showError(genreFld, 'Solo lettere e spazi');
        return;
      }
      if (!/^\d{1,2}$/.test(pegiFld.value.trim())) {
        e.preventDefault();
        showError(pegiFld, 'Valore non valido');
        return;
      }
      if (!formatFld.value) {
        e.preventDefault();
        showError(formatFld, 'Seleziona un formato');
        return;
      }
      if (!/^[1-9]\d*$/.test(qtyFld.value.trim())) {
        e.preventDefault();
        showError(qtyFld, 'Intero positivo richiesto');
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
