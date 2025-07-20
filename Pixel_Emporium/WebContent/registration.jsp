<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
   <meta charset="UTF-8">
   <title>Registrazione Utente - Pixel Emporium</title>
   <link rel="icon" type="image/png" href="<%=request.getContextPath()%>/media/icon.png">
    <link href="css/registrazione.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="page">
<div class="header">
	<%@ include file="/Header.jsp" %>
</div>
<div class="body">
   <h1>Registrazione</h1>
   
   <% 
       String message = (String) request.getAttribute("message");
       if (message != null) { 
   %>
       <p style="color:red;"><%= message %></p>
   <% } %>
   
   <form id="registerForm" action="Register" method="post" novalidate>
       <label for="email">Email:</label>
       <input type="email" id="email" name="email"
              placeholder="esempio@dominio.com" required>
       <span class="error" id="emailError"></span><br><br>
       
       <label for="nome">Nome:</label>
       <input type="text" id="nome" name="nome"
              placeholder="Solo lettere" required>
       <span class="error" id="nomeError"></span><br><br>
       
       <label for="cognome">Cognome:</label>
       <input type="text" id="cognome" name="cognome"
              placeholder="Solo lettere" required>
       <span class="error" id="cognomeError"></span><br><br>
       
       <label for="dataNascita">Data Nascita:</label>
       <input type="date" id="dataNascita" name="dataNascita"
              placeholder="yyyy-MM-dd" required>
       <span class="error" id="dataNascitaError"></span><br><br>
       
       <label>Genere:</label>
       <input type="radio" id="genM" name="genere" value="M" checked>
       <label for="genM">Maschio</label>
       <input type="radio" id="genF" name="genere" value="F">
       <label for="genF">Femmina</label>
       <span class="error" id="genereError"></span><br><br>
       
       <label for="indirizzo">Indirizzo:</label>
       <input type="text" id="indirizzo" name="indirizzo"
              placeholder="Via e numero civico" required>
       <span class="error" id="indirizzoError"></span><br><br>
       
       <label for="citta">Città:</label>
       <input type="text" id="citta" name="citta"
              placeholder="Solo lettere" required>
       <span class="error" id="cittaError"></span><br><br>
       
       <label for="prov">Provincia:</label>
       <input type="text" id="prov" name="prov"
              placeholder="2 lettere maiuscole" maxlength="2" required>
       <span class="error" id="provError"></span><br><br>
       
       <label for="cap">CAP:</label>
       <input type="text" id="cap" name="cap"
              placeholder="5 cifre" maxlength="5" required>
       <span class="error" id="capError"></span><br><br>
       
       <label for="password">Password:</label>
       <input type="password" id="password" name="password"
              placeholder="Min 6 caratteri, 1 cifra" required>
       <span class="error" id="passwordError"></span><br><br>
       
       <input type="submit" value="Registrati">
   </form>
   
   <br>
   <a href="login.jsp" id="login">Sei già registrato? Vai al login</a>

   <script>
	document.addEventListener('DOMContentLoaded', function() {
	  var form        = document.getElementById('registerForm');
	  var emailField  = document.getElementById('email');
	  var emailError  = document.getElementById('emailError');
	  var nome        = document.getElementById('nome');
	  var cognome     = document.getElementById('cognome');
	  var dataNasc    = document.getElementById('dataNascita');
	  var genereEls   = document.getElementsByName('genere');
	  var indirizzo   = document.getElementById('indirizzo');
	  var citta       = document.getElementById('citta');
	  var prov        = document.getElementById('prov');
	  var cap         = document.getElementById('cap');
	  var password    = document.getElementById('password');
	
	  var emailExistsFlag = false;
	  var debounceTimer;
	
	  function setError(el, msg) {
	    var err = document.getElementById(el.id + 'Error');
	    err.textContent = msg;
	    err.style.color = 'red';
	    el.focus();
	  }
	
	  function clearError(el) {
	    var err = document.getElementById(el.id + 'Error');
	    err.textContent = '';
	  }
	
	  // Pulisci errori sui campi man mano che l’utente scrive
	  [emailField, nome, cognome, dataNasc, indirizzo, citta, prov, cap, password]
	    .forEach(function(i){
	      i.addEventListener('input', function(){
	        clearError(i);
	      });
	    });
	
	  // AJAX + debounce per controllare l’email
	  emailField.addEventListener('input', function() {
	    clearTimeout(debounceTimer);
	    clearError(emailField);
	    emailExistsFlag = false;
	
	    if (!emailField.checkValidity()) return;
	
	    debounceTimer = setTimeout(function() {
	      fetch('<%= request.getContextPath() %>/CheckEmail?email='
	            + encodeURIComponent(emailField.value.trim()))
	        .then(function(res){ return res.json(); })
	        .then(function(data){
	          emailExistsFlag = data.exists;
	          if (data.exists) {
	            setError(emailField, 'Email già registrata');
	          }
	        })
	        .catch(function(err){
	          console.error('Errore verifica email:', err);
	        });
	    }, 400);
	  });
	
	  // Validazioni e blocco submit
	  form.addEventListener('submit', function(e) {
	
	    // 1) blocca subito se ajax ha già segnato email duplicata
	    if (emailExistsFlag) {
	      e.preventDefault();
	      return;
	    }
	
	    // 2) validazioni esistenti
	    if (!emailField.checkValidity()) {
	      e.preventDefault();
	      setError(emailField, 'Email non valida');
	      return;
	    }
	
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
	    if (!/^\d{4}-\d{2}-\d{2}$/.test(dataNasc.value)
	        || dataNasc.value > today) {
	      e.preventDefault();
	      setError(dataNasc, 'Data non valida');
	      return;
	    }
	
	    var chosen = Array.prototype.find.call(genereEls, function(r){ return r.checked; });
	    if (!chosen) {
	      e.preventDefault();
	      document.getElementById('genereError').textContent = 'Seleziona genere';
	      return;
	    }
	
	    if (indirizzo.value.trim() === '') {
	      e.preventDefault();
	      setError(indirizzo, 'Obbligatorio');
	      return;
	    }
	
	    if (!/^[A-Za-z ]+$/.test(citta.value.trim())) {
	      e.preventDefault();
	      setError(citta, 'Solo lettere');
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
	
	    if (!/^(?=.*\d)(?=.*[A-Za-z]).{6,}$/.test(password.value)) {
	      e.preventDefault();
	      setError(password, 'Min 6 caratteri e 1 cifra');
	      return;
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
