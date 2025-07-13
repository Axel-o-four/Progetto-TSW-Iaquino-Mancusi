<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,it.unisa.Model.OrdineBean" %>

<%
    Collection<OrdineBean> ordini = (Collection<OrdineBean>) request.getAttribute("ordini");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Elenco Ordini - Pixel Emporium</title>
	<link rel="icon" type="image/png" href="<%=request.getContextPath()%>/media/icon.png">
    <link href="<%=request.getContextPath() %>/css/ordiniAdmin.css" rel="stylesheet" type="text/css"></head>
<body>
<div class="page">
<div class="header">
	<%@ include file="/Header.jsp" %>
</div>
<div class="body">
    <h1>Elenco Ordini</h1>
    <%
        if (ordini == null || ordini.isEmpty()) {
    %>
        <p>Nessun ordine trovato.</p>
    <%
        } else {
    %>
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Email Utente</th>
                <th>Data Ordine</th>
                <th>Quantità</th>
                <th>Importo</th>
                <th>IVA</th>
                <th>Totale IVA</th>
                <th>Totale Fattura</th>
                <th>Indirizzo</th>
                <th>Metodo di Pagamento</th>
                <th>Azione</th>
            </tr>
            <%
                for (OrdineBean ordine : ordini) {
            %>
            <tr>
                <td><%= ordine.getId() %></td>
                <td><%= ordine.getEmailUtente() %></td>
                <td><%= ordine.getDataOrdine() %></td>
                <td><%= ordine.getQuantita() %></td>
                <td><%= ordine.getImporto() %>€</td>
                <td><%= ordine.getIva() %></td>
                <td><%= ordine.getTotaleIva() %>€</td>
                <td><%= ordine.getTotaleFattura() %>€</td>
                <td>
                    <%= ordine.getVia() %>, Nº <%= ordine.getNumeroCivico() %><br/>
                    <%= ordine.getCap() %>, <%= ordine.getCitta() %> (<%= ordine.getProvincia() %>)<br/>
                    <%= ordine.getPaese() %>
                </td>
                <td><%= ordine.getTipoPagamento() %></td>
                <td>
			      <a href="<%= request.getContextPath() %>/OrdineControl?action=read&id=<%= ordine.getId() %>">
			        Visualizza
			      </a>
			    </td>
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
