<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,it.unisa.Model.DettaglioOrdineBean,it.unisa.Model.OrdineBean" %>

<%
    OrdineBean ordine = (OrdineBean) request.getAttribute("ordine");
    Collection<DettaglioOrdineBean> dettagli = (Collection<DettaglioOrdineBean>) request.getAttribute("dettagli");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dettaglio Ordine - Pixel Emporium</title>
    <link rel="icon" type="image/png" href="<%=request.getContextPath()%>/media/icon.png">
	<link href="<%= request.getContextPath() %>/css/ordiniAdmin.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="page">
<div class="header">
	<%@ include file="/Header.jsp" %>
</div>
<div class="body">
    <h1>Dettaglio Ordine</h1>
    
    <!-- Inserisci il link per stampare la fattura PDF -->
    <p>
        <a href="invoicePDF?id=<%= ordine.getId() %>&emailUtente=<%= ordine.getEmailUtente() %>">
            Stampa Fattura PDF
        </a>
    </p>
    
    <%
        if (dettagli == null || dettagli.isEmpty()) {
    %>
        <p>Nessun dettaglio trovato per questo ordine.</p>
    <%
        } else {
    %>
        <table border="1">
            <tr>
                <th>Prodotto</th>
                <th>Quantità</th>
                <th>Prezzo Unitario</th>
                <th>Immagine</th>
                <th>Descrizione</th>
            </tr>
            <%
                for (DettaglioOrdineBean dettaglio : dettagli) {
            %>
            <tr>
                <td><%= dettaglio.getNome() %></td>
                <td><%= dettaglio.getQuantita() %></td>
                <td><%= String.format("%.2f", dettaglio.getPrezzoUnitario()) %>€</td>
                <td><img src="<%= dettaglio.getImmagine() %>" alt="Immagine prodotto" width="100"/></td>
                <td><%= dettaglio.getDescrizione() %></td>
            </tr>
            <%
                }
            %>
        </table>
    <%
        }
    %>
    </div>
    </div>
    <div class="footer">
        <%@ include file="/Footer.jsp" %>
    </div>
</body>
</html>
