<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, it.unisa.Cart, it.unisa.Item, it.unisa.GiocoBean, it.unisa.ConsoleBean, it.unisa.AccessorioBean" %>
<%@ include file="Header.jsp" %>

<%
    Cart cart = (it.unisa.Cart) session.getAttribute("cart");
    if (cart == null) {
        cart = new it.unisa.Cart();
        session.setAttribute("cart", cart);
    }
    List<Item> items = cart.getProducts();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Carrello - Pixel Emporium</title>
    <link href="ProductStyle.css" rel="stylesheet" type="text/css">
</head>
<body>
    <h2>Carrello</h2>
    <%
        if (items == null || items.isEmpty()) {
    %>
        <p>Il carrello è vuoto.</p>
    <%
        } else {
    %>
        <table border="1">
            <tr>
                <th>Nome</th>
                <th>Quantità</th>
                <th>Azione</th>
            </tr>
            <%
                for (Item item : items) {
                    String controller = "";
                    if (item instanceof GiocoBean) {
                        controller = "GiocoControl";
                    } else if (item instanceof ConsoleBean) {
                        controller = "ConsoleControl";
                    } else if (item instanceof AccessorioBean) {
                        controller = "AccessorioControl";
                    }
            %>
            <tr>
                <td><%= item.getName() %></td>
                <td><%= item.getQuantity() %></td>
                <td>
                    <a href="<%= controller %>?action=deleteC&id=<%= item.getCode() %>">
                        Elimina dal carrello
                    </a>
                </td>
            </tr>
            <%
                }
            %>
        </table>
        
        <hr/>
        <p>Totale articoli: <%= cart.getTotalQuantity() %></p>
        <p>Totale prezzo: <%= cart.getTotalPrice() %>€</p>
        
        <form action="Checkout.jsp" method="get">
            <input type="submit" value="Procedi al checkout">
        </form>
    <%
        }
    %>
    <%@ include file="Footer.jsp" %>
</body>
</html>
