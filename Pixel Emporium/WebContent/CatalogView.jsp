<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, it.unisa.GiocoBean, it.unisa.ConsoleBean, it.unisa.AccessorioBean, it.unisa.Cart" %>
<%@ include file="Header.jsp" %>

<%
    Collection<?> giochi = (Collection<?>) request.getAttribute("giochi");
    Collection<?> consoles = (Collection<?>) request.getAttribute("consoles");
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

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Catalogo Giochi, Console e Accessori - Pixel Emporium</title>
    <link href="ProductStyle.css" rel="stylesheet" type="text/css">
</head>
<body>
    <h2>Catalogo Prodotti</h2>
    
    <h3>Giochi</h3>
    <table border="1">
        <tr>
            <th>Immagine</th>
            <th>Descrizione</th>
            <th>Prezzo</th>
            <th>Quantità da aggiungere</th>
            <th>Azione</th>
        </tr>
        <%
            for (Object obj : giochi) {
                GiocoBean gioco = (GiocoBean) obj;
        %>
        <tr>
            <td><img src="<%= gioco.getImage() %>" alt="<%= gioco.getName() %>" width="100"></td>
            <td><%= gioco.getDescription() %></td>
            <td><%= gioco.getPrice() %>€</td>
            <td>
                <form action="GiocoControl" method="get">
                    <input type="hidden" name="action" value="addC">
                    <input type="hidden" name="id" value="<%= gioco.getCode() %>">
                    <input type="number" name="quantity" min="1" value="1" style="width:50px">
                    <input type="submit" value="Aggiungi">
                </form>
            </td>
            <td>
                <a href="GiocoControl?action=read&id=<%= gioco.getCode() %>">Dettagli</a>
            </td>
        </tr>
        <%
            }
        %>
    </table>
    
    <h3>Console</h3>
    <table border="1">
        <tr>
            <th>Immagine</th>
            <th>Descrizione</th>
            <th>Prezzo</th>
            <th>Quantità da aggiungere</th>
            <th>Azione</th>
        </tr>
        <%
            for (Object obj : consoles) {
                ConsoleBean console = (ConsoleBean) obj;
        %>
        <tr>
            <td><img src="<%= console.getImage() %>" alt="<%= console.getName() %>" width="100"></td>
            <td><%= console.getDescription() %></td>
            <td><%= console.getPrice() %>€</td>
            <td>
                <form action="ConsoleControl" method="get">
                    <input type="hidden" name="action" value="addC">
                    <input type="hidden" name="id" value="<%= console.getCode() %>">
                    <input type="number" name="quantity" min="1" value="1" style="width:50px">
                    <input type="submit" value="Aggiungi">
                </form>
            </td>
            <td>
                <a href="ConsoleControl?action=read&id=<%= console.getCode() %>">Dettagli</a>
            </td>
        </tr>
        <%
            }
        %>
    </table>
    
    <h3>Accessori</h3>
    <table border="1">
        <tr>
            <th>Immagine</th>
            <th>Descrizione</th>
            <th>Prezzo</th>
            <th>Quantità da aggiungere</th>
            <th>Azione</th>
        </tr>
        <%
            for (Object obj : accessori) {
                AccessorioBean accessorio = (AccessorioBean) obj;
        %>
        <tr>
            <td><img src="<%= accessorio.getImage() %>" alt="<%= accessorio.getName() %>" width="100"></td>
            <td><%= accessorio.getDescription() %></td>
            <td><%= accessorio.getPrice() %>€</td>
            <td>
                <form action="AccessorioControl" method="get">
                    <input type="hidden" name="action" value="addC">
                    <input type="hidden" name="id" value="<%= accessorio.getCode() %>">
                    <input type="number" name="quantity" min="1" value="1" style="width:50px">
                    <input type="submit" value="Aggiungi">
                </form>
            </td>
            <td>
                <a href="AccessorioControl?action=read&id=<%= accessorio.getCode() %>">Dettagli</a>
            </td>
        </tr>
        <%
            }
        %>
    </table>
    
    <%@ include file="Footer.jsp" %>
</body>
</html>
