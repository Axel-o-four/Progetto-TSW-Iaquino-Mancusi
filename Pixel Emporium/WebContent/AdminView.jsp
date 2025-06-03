<%@ page import="java.util.*, it.unisa.GiocoBean, it.unisa.ConsoleBean, it.unisa.AccessorioBean" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="Header.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="ProductStyle.css" rel="stylesheet" type="text/css">
    <title>Gestione Catalogo - Pixel Emporium</title>
</head>
<body>
    <h2>Gestione Catalogo</h2>

    <h3>Inserisci Videogioco</h3>
    <form action="GiocoControl" method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="insert">
        <input type="hidden" name="prefissoId" value="G">
        
        <label for="name">Nome:</label>
        <input name="name" type="text" maxlength="255" required><br>

        <label for="description">Descrizione:</label>
        <textarea name="description" maxlength="10000" required></textarea><br>

        <label for="image">Immagine:</label>
        <input name="image" type="file" accept="image/*" required><br>

        <label for="brand">Marchio:</label>
        <input name="brand" type="text" maxlength="255" required><br>

        <label for="price">Prezzo:</label>
        <input name="price" type="number" step="0.01" min="0" required><br>

        <label for="releaseYear">Anno di rilascio:</label>
        <input name="releaseYear" type="number" min="1980" max="2025" required><br>

        <label for="genre">Genere:</label>
        <input name="genre" type="text" maxlength="255"><br>

        <label for="pegi">PEGI:</label>
        <input name="pegi" type="text" maxlength="2"><br>

        <label for="format">Formato:</label>
        <select name="format" required>
            <option value="FISICO">FISICO</option>
            <option value="DIGITALE">DIGITALE</option>
        </select><br>

        <label for="quantity">Quantità:</label>
        <input name="quantity" type="number" min="1" required><br>

        <input type="submit" value="Aggiungi Videogioco">
    </form>

    <h3>Inserisci Console</h3>
    <form action="ConsoleControl" method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="insert">
        <input type="hidden" name="prefissoId" value="C">
        
        <label for="name">Nome:</label>
        <input name="name" type="text" maxlength="255" required><br>

        <label for="description">Descrizione:</label>
        <textarea name="description" maxlength="10000" required></textarea><br>

        <label for="image">Immagine:</label>
        <input name="image" type="file" accept="image/*" required><br>

        <label for="brand">Marchio:</label>
        <input name="brand" type="text" maxlength="255" required><br>

        <label for="price">Prezzo:</label>
        <input name="price" type="number" step="0.01" min="0" required><br>

        <label for="releaseYear">Anno di rilascio:</label>
        <input name="releaseYear" type="number" min="1980" max="2025" required><br>

        <label for="support">Supporti:</label>
        <select name="support" required>
            <option value="DISCHI">DISCHI</option>
            <option value="CARTUCCIA">CARTUCCIA</option>
            <option value="SOLO DIGITALE">SOLO DIGITALE</option>
        </select><br>

        <label for="retroCompatibility">Retrocompatibilità:</label>
        <select name="retroCompatibility" required>
            <option value="true">Sì</option>
            <option value="false">No</option>
        </select><br>

        <label for="storage">Archiviazione:</label>
        <input name="storage" type="text" maxlength="255"><br>

        <label for="generation">Generazione:</label>
        <input name="generation" type="number" min="1"><br>

        <label for="quantity">Quantità:</label>
        <input name="quantity" type="number" min="1" required><br>

        <input type="submit" value="Aggiungi Console">
    </form>
    
    <h3>Inserisci Accessorio</h3>
	<form action="AccessorioControl" method="post" enctype="multipart/form-data">
	    <input type="hidden" name="action" value="insert">
	    <input type="hidden" name="prefissoId" value="A">
	    
	    <label for="name">Nome:</label><br>
	    <input name="name" type="text" maxlength="255" required placeholder="Inserisci nome"><br>
	    
	    <label for="description">Descrizione:</label><br>
	    <textarea name="description" maxlength="10000" rows="3" required placeholder="Inserisci descrizione"></textarea><br>
	    
	    <label for="image">Immagine:</label><br>
	    <input name="image" type="file" accept="image/*" required placeholder="Inserisci immagine"><br>
	    
	    <label for="brand">Marchio:</label><br>
	    <input name="brand" type="text" maxlength="255" required placeholder="Inserisci marchio"><br>
	    
	    <label for="price">Prezzo:</label><br>
	    <input name="price" type="number" step="0.01" min="0" required placeholder="Inserisci prezzo"><br>
	    
	    <label for="accessoryType">Tipo Accessorio:</label><br>
	    <input name="accessoryType" type="text" maxlength="255" required placeholder="Inserisci tipo accessorio"><br>
	    
	    <label for="quantity">Quantità:</label><br>
	    <input name="quantity" type="number" min="1" required placeholder="Inserisci quantità"><br>
	    
	    <input type="submit" value="Aggiungi Accessorio">
	</form>

    
    <%@ include file="Footer.jsp" %>
</body>
</html>
