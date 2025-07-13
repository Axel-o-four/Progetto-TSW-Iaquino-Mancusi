<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.*,
                   it.unisa.Model.GiocoBean,
                   it.unisa.Model.ConsoleBean,
                   it.unisa.Model.AccessorioBean,
                   it.unisa.Model.GiocoModelDS,
                   it.unisa.Model.ConsoleModelDS,
                   it.unisa.Model.AccessorioModelDS" %>

<%
  Collection<GiocoBean> giochi = (Collection<GiocoBean>) request.getAttribute("giochi");
  if (giochi == null) {
      giochi = new GiocoModelDS().doRetrieveAll(null);
      request.setAttribute("giochi", giochi);
  }
  Collection<ConsoleBean> consoles = (Collection<ConsoleBean>) request.getAttribute("consoles");
  if (consoles == null) {
      consoles = new ConsoleModelDS().doRetrieveAll(null);
      request.setAttribute("consoles", consoles);
  }
  Collection<AccessorioBean> accessori = (Collection<AccessorioBean>) request.getAttribute("accessori");
  if (accessori == null) {
      accessori = new AccessorioModelDS().doRetrieveAll(null);
      request.setAttribute("accessori", accessori);
  }
%>
<head>
	<title>Gestione Catalogo - Pixel Emporium</title>
  	<link rel="icon" type="image/png" href="<%=request.getContextPath()%>/media/icon.png">
	<link href="<%= request.getContextPath() %>/css/gestcat.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="page">
		<div class="header">
			<%@ include file="/Header.jsp" %>
		</div>
		<div class="body">
<h1>Gestione Catalogo</h1>

<h3>1. Inserisci Videogioco</h3>
<form id="insertGameForm"
      action="<%= request.getContextPath() %>/GiocoControl"
      method="post"
      enctype="multipart/form-data"
      novalidate>
  <input type="hidden" name="action" value="insert">
  <input type="hidden" name="prefissoId" value="G">

  <label>Nome:</label>
  <input type="text" id="gName" name="name"
         placeholder="2–100 caratteri (lettere, numeri e spazi)" required>
  <span id="gNameError" class="error"></span><br><br>

  <label>Descrizione:</label>
  <textarea id="gDescription" name="description" rows="3"
            placeholder="Almeno 5 caratteri" required></textarea>
  <span id="gDescriptionError" class="error"></span><br><br>

  <label>Immagine:</label>
  <input type="file" id="gImage" name="image" accept="image/*" required>
  <span id="gImageError" class="error"></span><br><br>

  <label>Marchio:</label>
  <input type="text" id="gBrand" name="brand"
         placeholder="2–50 caratteri" required>
  <span id="gBrandError" class="error"></span><br><br>

  <label>Prezzo (€):</label>
  <input type="number" id="gPrice" name="price"
         placeholder="Formato 0.00" step="0.01" min="0" required>
  <span id="gPriceError" class="error"></span><br><br>

  <label>Anno di rilascio:</label>
  <input type="number" id="gYear" name="releaseYear"
         placeholder="1980–2025" min="1980" max="2025" required>
  <span id="gYearError" class="error"></span><br><br>

  <label>Genere:</label>
  <input type="text" id="gGenre" name="genre"
         placeholder="Solo lettere e spazi" required>
  <span id="gGenreError" class="error"></span><br><br>

  <label>PEGI:</label>
  <input type="text" id="gPegi" name="pegi"
         placeholder="Numero 1–2 cifre" maxlength="2" required>
  <span id="gPegiError" class="error"></span><br><br>

  <label>Formato:</label>
  <select id="gFormat" name="format" required>
    <option value="">Seleziona…</option>
    <option value="FISICO">FISICO</option>
    <option value="DIGITALE">DIGITALE</option>
  </select>
  <span id="gFormatError" class="error"></span><br><br>

  <label>Quantità:</label>
  <input type="number" id="gQty" name="quantity"
         placeholder="Intero positivo" min="1" required>
  <span id="gQtyError" class="error"></span><br><br>

  <button type="submit">Aggiungi Videogioco</button>
</form>

<hr>

<h3>2. Inserisci Console</h3>
<form id="insertConsoleForm"
      action="<%= request.getContextPath() %>/ConsoleControl"
      method="post"
      enctype="multipart/form-data"
      novalidate>
  <input type="hidden" name="action" value="insert">
  <input type="hidden" name="prefissoId" value="C">

  <label>Nome:</label>
  <input type="text" id="cName" name="name"
         placeholder="2–100 caratteri" required>
  <span id="cNameError" class="error"></span><br><br>

  <label>Descrizione:</label>
  <textarea id="cDescription" name="description" rows="3"
            placeholder="Almeno 5 caratteri" required></textarea>
  <span id="cDescriptionError" class="error"></span><br><br>

  <label>Immagine:</label>
  <input type="file" id="cImage" name="image" accept="image/*" required>
  <span id="cImageError" class="error"></span><br><br>

  <label>Marchio:</label>
  <input type="text" id="cBrand" name="brand"
         placeholder="2–50 caratteri" required>
  <span id="cBrandError" class="error"></span><br><br>

  <label>Prezzo (€):</label>
  <input type="number" id="cPrice" name="price"
         placeholder="Formato 0.00" step="0.01" min="0" required>
  <span id="cPriceError" class="error"></span><br><br>

  <label>Anno di rilascio:</label>
  <input type="number" id="cYear" name="releaseYear"
         placeholder="1980–2025" min="1980" max="2025" required>
  <span id="cYearError" class="error"></span><br><br>

  <label>Supporti:</label>
  <select id="cSupport" name="support" required>
    <option value="">Seleziona…</option>
    <option value="DISCHI">DISCHI</option>
    <option value="CARTUCCIA">CARTUCCIA</option>
    <option value="SOLO DIGITALE">SOLO DIGITALE</option>
  </select>
  <span id="cSupportError" class="error"></span><br><br>

  <label>Retrocompatibilità:</label>
  <select id="cRetro" name="retroCompatibility" required>
    <option value="">Seleziona…</option>
    <option value="true">Sì</option>
    <option value="false">No</option>
  </select>
  <span id="cRetroError" class="error"></span><br><br>

  <label>Archiviazione:</label>
  <input type="text" id="cStorage" name="storage"
         placeholder="Es. 500GB, 1TB">
  <span id="cStorageError" class="error"></span><br><br>

  <label>Generazione:</label>
  <input type="number" id="cGen" name="generation"
         placeholder="Intero positivo" min="1">
  <span id="cGenError" class="error"></span><br><br>

  <label>Quantità:</label>
  <input type="number" id="cQty" name="quantity"
         placeholder="Intero positivo" min="1" required>
  <span id="cQtyError" class="error"></span><br><br>

  <button type="submit">Aggiungi Console</button>
</form>

<hr>

<h3>3. Inserisci Accessorio</h3>
<form id="insertAccForm"
      action="<%= request.getContextPath() %>/AccessorioControl"
      method="post"
      enctype="multipart/form-data"
      novalidate>
  <input type="hidden" name="action" value="insert">
  <input type="hidden" name="prefissoId" value="A">

  <label>Nome:</label>
  <input type="text" id="aName" name="name"
         placeholder="2–100 caratteri" required>
  <span id="aNameError" class="error"></span><br><br>

  <label>Descrizione:</label>
  <textarea id="aDescription" name="description" rows="3"
            placeholder="Almeno 5 caratteri" required></textarea>
  <span id="aDescriptionError" class="error"></span><br><br>

  <label>Immagine:</label>
  <input type="file" id="aImage" name="image" accept="image/*" required>
  <span id="aImageError" class="error"></span><br><br>

  <label>Marchio:</label>
  <input type="text" id="aBrand" name="brand"
         placeholder="2–50 caratteri" required>
  <span id="aBrandError" class="error"></span><br><br>

  <label>Prezzo (€):</label>
  <input type="number" id="aPrice" name="price"
         placeholder="Formato 0.00" step="0.01" min="0" required>
  <span id="aPriceError" class="error"></span><br><br>

  <label>Tipo Accessorio:</label>
  <input type="text" id="aType" name="accessoryType"
         placeholder="2–50 caratteri" required>
  <span id="aTypeError" class="error"></span><br><br>

  <label>Quantità:</label>
  <input type="number" id="aQty" name="quantity"
         placeholder="Intero positivo" min="1" required>
  <span id="aQtyError" class="error"></span><br><br>

  <button type="submit">Aggiungi Accessorio</button>
</form>

<hr/>

<h3>Elenco Videogiochi</h3>
<table border="1">
  <tr>
    <th>Codice</th><th>Nome</th><th>Prezzo</th><th>Q.tà</th><th>Azioni</th>
  </tr>
  <%
    for (GiocoBean g : giochi) {
  %>
  <tr>
    <td><%= g.getCode() %></td>
    <td><%= g.getName() %></td>
    <td><%= String.format("%.2f €", g.getPrice()) %></td>
    <td><%= g.getQuantity() %></td>
    <td>
      <a href="<%=request.getContextPath()%>/GiocoControl?action=edit&id=<%=g.getCode()%>">
        Modifica
      </a> |
      <a href="<%=request.getContextPath()%>/GiocoControl?action=delete&id=<%=g.getCode()%>"
         onclick="return confirm('Eliminare &quot;<%=g.getName()%>&quot;?');">
        Elimina
      </a>
    </td>
  </tr>
  <%
    }
  %>
</table>

<h3>Elenco Console</h3>
<table border="1">
  <tr>
    <th>Codice</th><th>Nome</th><th>Prezzo</th><th>Q.tà</th><th>Azioni</th>
  </tr>
  <%
    for (ConsoleBean c : consoles) {
  %>
  <tr>
    <td><%= c.getCode() %></td>
    <td><%= c.getName() %></td>
    <td><%= String.format("%.2f €", c.getPrice()) %></td>
    <td><%= c.getQuantity() %></td>
    <td>
      <a href="<%=request.getContextPath()%>/ConsoleControl?action=edit&id=<%=c.getCode()%>">
        Modifica
      </a> |
      <a href="<%=request.getContextPath()%>/ConsoleControl?action=delete&id=<%=c.getCode()%>">
        Elimina
      </a>
    </td>
  </tr>
  <%
    }
  %>
</table>

<h3>Elenco Accessori</h3>
<table border="1">
  <tr>
    <th>Codice</th><th>Nome</th><th>Prezzo</th><th>Q.tà</th><th>Azioni</th>
  </tr>
  <%
    for (AccessorioBean a : accessori) {
  %>
  <tr>
    <td><%= a.getCode() %></td>
    <td><%= a.getName() %></td>
    <td><%= String.format("%.2f €", a.getPrice()) %></td>
    <td><%= a.getQuantity() %></td>
    <td>
      <a href="<%=request.getContextPath()%>/AccessorioControl?action=edit&id=<%=a.getCode()%>">
        Modifica
      </a> |
      <a href="<%=request.getContextPath()%>/AccessorioControl?action=delete&id=<%=a.getCode()%>">
        Elimina
      </a>
    </td>
  </tr>
  <%
    }
  %>
</table>
</div>
</div>
</body>
<div class="footer">
	<%@ include file="/Footer.jsp" %>
</div>

<script>
document.addEventListener('DOMContentLoaded', function() {
  function setError(el, msg) {
    const err = document.getElementById(el.id + 'Error');
    err.textContent = msg;
    el.focus();
  }
  function clearError(el) {
    const err = document.getElementById(el.id + 'Error');
    err.textContent = '';
  }

  (function(){
    const form = document.getElementById('insertGameForm');
    const name = document.getElementById('gName');
    const desc = document.getElementById('gDescription');
    const img  = document.getElementById('gImage');
    const brand= document.getElementById('gBrand');
    const price= document.getElementById('gPrice');
    const year = document.getElementById('gYear');
    const genre= document.getElementById('gGenre');
    const pegi = document.getElementById('gPegi');
    const fmt  = document.getElementById('gFormat');
    const qty  = document.getElementById('gQty');

    [name,desc,img,brand,price,year,genre,pegi,fmt,qty]
      .forEach(f => f.addEventListener('input', () => clearError(f)));

    form.addEventListener('submit', function(e){
      if (!/^[\w ]{2,100}$/.test(name.value.trim())) {
        e.preventDefault(); setError(name,'Nome non valido'); return;
      }
      if (desc.value.trim().length < 5) {
        e.preventDefault(); setError(desc,'Descrizione troppo breve'); return;
      }
      if (img.files.length===0) {
        e.preventDefault(); setError(img,'Seleziona un file'); return;
      }
      if (!/^[\w ]{2,50}$/.test(brand.value.trim())) {
        e.preventDefault(); setError(brand,'Marchio non valido'); return;
      }
      if (!/^\d+(\.\d{1,2})?$/.test(price.value.trim())) {
        e.preventDefault(); setError(price,'Formato prezzo errato'); return;
      }
      const y = parseInt(year.value,10);
      if (isNaN(y) || y<1980 || y>2025) {
        e.preventDefault(); setError(year,'Anno fuori intervallo'); return;
      }
      if (!/^[A-Za-z ]+$/.test(genre.value.trim())) {
        e.preventDefault(); setError(genre,'Solo lettere e spazi'); return;
      }
      if (!/^\d{1,2}$/.test(pegi.value.trim())) {
        e.preventDefault(); setError(pegi,'Valore PEGI non valido'); return;
      }
      if (!fmt.value) {
        e.preventDefault(); setError(fmt,'Seleziona formato'); return;
      }
      if (!/^[1-9]\d*$/.test(qty.value.trim())) {
        e.preventDefault(); setError(qty,'Intero positivo necessario'); return;
      }
    });
  })();

  (function(){
    const form = document.getElementById('insertConsoleForm');
    const name = document.getElementById('cName');
    const desc = document.getElementById('cDescription');
    const img  = document.getElementById('cImage');
    const brand= document.getElementById('cBrand');
    const price= document.getElementById('cPrice');
    const year = document.getElementById('cYear');
    const sup  = document.getElementById('cSupport');
    const retro= document.getElementById('cRetro');
    const stor = document.getElementById('cStorage');
    const gen  = document.getElementById('cGen');
    const qty  = document.getElementById('cQty');

    [name,desc,img,brand,price,year,sup,retro,stor,gen,qty]
      .forEach(f => f.addEventListener('input', () => clearError(f)));

    form.addEventListener('submit', function(e){
      if (!/^[\w ]{2,100}$/.test(name.value.trim())) {
        e.preventDefault(); setError(name,'Nome non valido'); return;
      }
      if (desc.value.trim().length<5) {
        e.preventDefault(); setError(desc,'Descrizione troppo breve'); return;
      }
      if (img.files.length===0) {
        e.preventDefault(); setError(img,'Seleziona un file'); return;
      }
      if (!/^[\w ]{2,50}$/.test(brand.value.trim())) {
        e.preventDefault(); setError(brand,'Marchio non valido'); return;
      }
      if (!/^\d+(\.\d{1,2})?$/.test(price.value.trim())) {
        e.preventDefault(); setError(price,'Formato prezzo errato'); return;
      }
      const y = parseInt(year.value,10);
      if (isNaN(y)||y<1980||y>2025) {
        e.preventDefault(); setError(year,'Anno fuori intervallo'); return;
      }
      if (!sup.value) {
        e.preventDefault(); setError(sup,'Seleziona supporti'); return;
      }
      if (!retro.value) {
        e.preventDefault(); setError(retro,'Seleziona retro'); return;
      }
      const s = stor.value.trim();
      if (s && (s.length<2||s.length>30)) {
        e.preventDefault(); setError(stor,'Archiviazione non valida'); return;
      }
      if (gen.value && !/^[1-9]\d*$/.test(gen.value.trim())) {
        e.preventDefault(); setError(gen,'Intero positivo richiesto'); return;
      }
      if (!/^[1-9]\d*$/.test(qty.value.trim())) {
        e.preventDefault(); setError(qty,'Intero positivo necessario'); return;
      }
    });
  })();

  (function(){
    const form = document.getElementById('insertAccForm');
    const name = document.getElementById('aName');
    const desc = document.getElementById('aDescription');
    const img  = document.getElementById('aImage');
    const brand= document.getElementById('aBrand');
    const price= document.getElementById('aPrice');
    const type = document.getElementById('aType');
    const qty  = document.getElementById('aQty');

    [name,desc,img,brand,price,type,qty]
      .forEach(f => f.addEventListener('input', () => clearError(f)));

    form.addEventListener('submit', function(e){
      if (!/^[\w ]{2,100}$/.test(name.value.trim())) {
        e.preventDefault(); setError(name,'Nome non valido'); return;
      }
      if (desc.value.trim().length<5) {
        e.preventDefault(); setError(desc,'Descrizione troppo breve'); return;
      }
      if (img.files.length===0) {
        e.preventDefault(); setError(img,'Seleziona un file'); return;
      }
      if (!/^[\w ]{2,50}$/.test(brand.value.trim())) {
        e.preventDefault(); setError(brand,'Marchio non valido'); return;
      }
      if (!/^\d+(\.\d{1,2})?$/.test(price.value.trim())) {
        e.preventDefault(); setError(price,'Formato prezzo errato'); return;
      }
      if (!/^[\w ]{2,50}$/.test(type.value.trim())) {
        e.preventDefault(); setError(type,'Tipo non valido'); return;
      }
      if (!/^[1-9]\d*$/.test(qty.value.trim())) {
        e.preventDefault(); setError(qty,'Intero positivo necessario'); return;
      }
    });
  })();
});
</script>
