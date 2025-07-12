<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,it.unisa.Model.DettaglioOrdineBean,it.unisa.Model.OrdineBean" %>
<%@ include file="/Header.jsp" %>

<%
    OrdineBean ordine = (OrdineBean) request.getAttribute("ordine");
    Collection<DettaglioOrdineBean> dettagli = (Collection<DettaglioOrdineBean>) request.getAttribute("dettagli");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dettaglio Ordine</title>
    <link href="ProductStyle.css" rel="stylesheet" type="text/css">
</head>
<body>
    <h2>Dettaglio Ordine</h2>
    
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
                <td><%= dettaglio.getPrezzoUnitario() %>€</td>
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
    <%@ include file="/Footer.jsp" %>
</body>
</html>
