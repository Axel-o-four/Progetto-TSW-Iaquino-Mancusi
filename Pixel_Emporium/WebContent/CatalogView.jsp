<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,it.unisa.Model.GiocoBean,it.unisa.Model.ConsoleBean,it.unisa.Model.AccessorioBean,it.unisa.Model.Cart, it.unisa.Model.UserBean" %>

<%
    Collection<?> giochi    = (Collection<?>) request.getAttribute("giochi");
    Collection<?> consoles  = (Collection<?>) request.getAttribute("consoles");
    Collection<?> accessori = (Collection<?>) request.getAttribute("accessori");

    if (giochi == null) {
        response.sendRedirect(request.getContextPath() + "/GiocoControl");
        return;
    }
    if (consoles == null) {
        response.sendRedirect(request.getContextPath() + "/ConsoleControl");
        return;
    }
    if (accessori == null) {
        response.sendRedirect(request.getContextPath() + "/AccessorioControl");
        return;
    }
    
    Cart cart = (Cart) session.getAttribute("cart");
    if (cart == null) {
        cart = new Cart();
        session.setAttribute("cart", cart);
    }
%>

<%
	UserBean user = (UserBean) session.getAttribute("user");
	String utente = "Ospite";
	if (user != null && user.getNome() != null) {
	    utente = user.getNome();
	}
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Catalogo Prodotti - Pixel Emporium</title>
    <link rel="icon" type="image/png" href="<%=request.getContextPath()%>/media/icon.png">
    <link href="css/catalogo.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="page">
	<div class="header">
		<%@ include file="Header.jsp" %>
	</div>
	<div class="body">
	<div id="banner">
	    <img src="media/alienEmoji.png" alt="alienEmoji">
	    <span id="benvenuto">Benvenuto, <i><%= utente %></i> !</span>
	</div>
  <h2>Catalogo Prodotti</h2>
  <div class="colonnaCatalogo">
  <h3>Giochi</h3>

	<input
	  type="text"
	  id="giochiSearch"
	  placeholder="Cerca giochi…"
	  autocomplete="off"
	/>
	<div id="giochiSuggestions" class="suggestions"></div>
	
	<table border="1">
	  <thead>
	    <tr>
	      <th>Oggetto</th>
	      <th>Descrizione</th>
	      <th>Prezzo</th>
	      <th>Quantità da aggiungere</th>
	      <th>Azione</th>
	    </tr>
	  </thead>
	  <tbody id="giochiTableBody">
	    <% for (Object obj : giochi) {
	         GiocoBean gioco = (GiocoBean) obj; %>
	      <tr>
	        <td>
	        <p><%=gioco.getName() %></p>
	          <img
	            src="<%= gioco.getImage() %>"
	            alt="<%= gioco.getName() %>"
	            width="100"
	          />
	        </td>
	        <td><%= gioco.getDescription() %></td>
	        <td><%= String.format("%.2f", gioco.getPrice()) %>€</td>
	        <td>
	          <form class="add-form" action="GiocoControl" method="get" novalidate>
	            <input type="hidden" name="action" value="addC"/>
	            <input type="hidden" name="id"     value="<%= gioco.getCode() %>"/>
	            <input
	              class="qty-input"
	              name="quantity"
	              type="number"
	              min="1"
	              value="1"
	              placeholder="Inserisci intero positivo"
	              style="width:50px"
	            />
	            <span class="error"></span>
	            <input type="submit" value="Aggiungi"/>
	          </form>
	        </td>
	        <td>
	          <a
	            href="GiocoControl?action=read&id=<%= gioco.getCode() %>"
	          >Dettagli</a>
	        </td>
	      </tr>
	    <% } %>
	  </tbody>
	</table>
  </div>
  <div class="colonnaCatalogo">
  <h3>Console</h3>

	<input
	  type="text"
	  id="consoleSearch"
	  placeholder="Cerca console…"
	  autocomplete="off"
	/>
	<div id="consoleSuggestions" class="suggestions"></div>
	
	<table border="1">
	  <thead>
	    <tr>
	      <th>Oggetto</th>
	      <th>Descrizione</th>
	      <th>Prezzo</th>
	      <th>Quantità da aggiungere</th>
	      <th>Azione</th>
	    </tr>
	  </thead>
	  <tbody id="consoleTableBody">
	    <% for (Object obj : consoles) {
	         ConsoleBean console = (ConsoleBean) obj; %>
	      <tr>
	        <td>
	        <p><%=console.getName() %></p>
	          <img
	            src="<%= console.getImage() %>"
	            alt="<%= console.getName() %>"
	            width="100"
	          />
	        </td>
	        <td><%= console.getDescription() %></td>
	        <td><%= String.format("%.2f", console.getPrice()) %>€</td>
	        <td>
	          <form
	            class="add-form"
	            action="ConsoleControl"
	            method="get"
	            novalidate
	          >
	            <input type="hidden" name="action" value="addC"/>
	            <input type="hidden" name="id"     value="<%= console.getCode() %>"/>
	            <input
	              class="qty-input"
	              name="quantity"
	              type="number"
	              min="1"
	              value="1"
	              placeholder="Inserisci intero positivo"
	              style="width:50px"
	            />
	            <span class="error"></span>
	            <input type="submit" value="Aggiungi"/>
	          </form>
	        </td>
	        <td>
	          <a
	            href="ConsoleControl?action=read&id=<%= console.getCode() %>"
	          >Dettagli</a>
	        </td>
	      </tr>
	    <% } %>
	  </tbody>
	</table>
	</div>
  	<div class="colonnaCatalogo">
  <h3>Accessori</h3>

	<input
	  type="text"
	  id="accessoriSearch"
	  placeholder="Cerca accessori…"
	  autocomplete="off"
	/>
	<div id="accessoriSuggestions" class="suggestions"></div>
	
	<table border="1">
	  <thead>
	    <tr>
	      <th>Oggetto</th>
	      <th>Descrizione</th>
	      <th>Prezzo</th>
	      <th>Quantità da aggiungere</th>
	      <th>Azione</th>
	    </tr>
	  </thead>
	  <tbody id="accessoriTableBody">
	    <% for (Object obj : accessori) {
	         AccessorioBean accessorio = (AccessorioBean) obj; %>
	      <tr>
	        <td>
	        	<p><%= accessorio.getName() %></p>
	          <img
	            src="<%= accessorio.getImage() %>"
	            alt="<%= accessorio.getName() %>"
	            width="100"
	          />
	        </td>
	        <td><%= accessorio.getDescription() %></td>
	        <td><%= String.format("%.2f", accessorio.getPrice()) %>€</td>
	        <td>
	          <form
	            class="add-form"
	            action="AccessorioControl"
	            method="get"
	            novalidate
	          >
	            <input type="hidden" name="action" value="addC"/>
	            <input type="hidden" name="id"     value="<%= accessorio.getCode() %>"/>
	            <input
	              class="qty-input"
	              name="quantity"
	              type="number"
	              min="1"
	              value="1"
	              placeholder="Inserisci intero positivo"
	              style="width:50px"
	            />
	            <span class="error"></span>
	            <input type="submit" value="Aggiungi"/>
	          </form>
	        </td>
	        <td>
	          <a
	            href="AccessorioControl?action=read&id=<%= accessorio.getCode() %>"
	          >Dettagli</a>
	        </td>
	      </tr>
	    <% } %>
	  </tbody>
	</table>
	</div>
	</div>
</div>
<div class="footer">
<%@ include file="Footer.jsp" %>
</div>
	<script>	
	  document.addEventListener('DOMContentLoaded', function() {
	    var forms = document.querySelectorAll('.add-form');
	    var re = /^[1-9]\d*$/;
	
	    forms.forEach(function(form) {
	      var qty = form.querySelector('.qty-input');
	      var err = form.querySelector('.error');
	
	      form.addEventListener('submit', function(evt) {
	        var val = qty.value.trim();
	        if (!re.test(val)) {
	          evt.preventDefault();
	          err.textContent = 'Inserisci un intero positivo.';
	          qty.focus();
	        }
	      });
	
	      qty.addEventListener('input', function() {
	        err.textContent = '';
	      });
	    });
	  });
	
	  // 2) Funzione debounce
	  function debounce(fn, delay) {
	    var timer = null;
	    return function() {
	      var args = arguments, self = this;
	      clearTimeout(timer);
	      timer = setTimeout(function() {
	        fn.apply(self, args);
	      }, delay);
	    };
	  }
	
	  // 3) Inizializza autocomplete e filtraggio su una tabella
	  function initSuggest(config) {
	    var input   = document.getElementById(config.inputId);
	    var suggBox = document.getElementById(config.suggId);
	    var tbody   = document.getElementById(config.tbodyId);
	
	    var doSearch = debounce(function(term) {
	      if (term.length < 2) {
	        suggBox.innerHTML = '';
	        filterTable('');
	        return;
	      }
	      var url = config.endpoint
	              + '?action=search&term='
	              + encodeURIComponent(term);
	
	      fetch(url)
	        .then(function(res) { return res.json(); })
	        .then(function(data) {
	          var html = '';
	          data.forEach(function(item) {
	            html += '<div data-name="'
	                 +  item.name
	                 +  '">'
	                 +  item.name
	                 +  '</div>';
	          });
	          suggBox.innerHTML = html;
	        })
	        .catch(function(err) {
	          console.error(err);
	        });
	    }, 300);
	
	    input.addEventListener('input', function(e) {
	      doSearch(e.target.value.trim().toLowerCase());
	    });
	    
	 // intercetta Invio per filtrare la tabella
	    input.addEventListener('keydown', function(e) {
	      if (e.key === 'Enter') {
	        e.preventDefault();                             // evita che il form venga inviato
	        var term = input.value.trim().toLowerCase();
	        suggBox.innerHTML = '';                          // nasconde i suggerimenti
	        filterTable(term);                               // esegue il filtro
	      }
	    });
	
	    suggBox.addEventListener('click', function(e) {
	      var name = e.target.getAttribute('data-name');
	      if (!name) return;
	      input.value = name;
	      suggBox.innerHTML = '';
	      filterTable(name.toLowerCase());
	    });
	
	    document.addEventListener('click', function(e) {
	      if (e.target !== input) {
	        suggBox.innerHTML = '';
	      }
	    });
	
	    function filterTable(term) {
	      var rows = tbody.rows;
	      for (var i = 0; i < rows.length; i++) {
	        var text = rows[i].textContent.toLowerCase();
	        rows[i].style.display = (term === '' || text.indexOf(term) !== -1)
	                              ? '' : 'none';
	      }
	    }
	  }
	
	  // 4) All’avvio, setta tutti e tre gli autocomplete
	  document.addEventListener('DOMContentLoaded', function() {
	    initSuggest({
	      inputId:  'giochiSearch',
	      suggId:   'giochiSuggestions',
	      tbodyId:  'giochiTableBody',
	      endpoint: 'GiocoControl'
	    });
	    initSuggest({
	      inputId:  'consoleSearch',
	      suggId:   'consoleSuggestions',
	      tbodyId:  'consoleTableBody',
	      endpoint: 'ConsoleControl'
	    });
	    initSuggest({
	      inputId:  'accessoriSearch',
	      suggId:   'accessoriSuggestions',
	      tbodyId:  'accessoriTableBody',
	      endpoint: 'AccessorioControl'
	    });
	  });
	</script>

</body>
</html>

