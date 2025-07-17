package it.unisa.Control;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.SQLException;
import it.unisa.Model.Cart;
import it.unisa.Model.Item;
import it.unisa.Model.GiocoBean;
import it.unisa.Model.ConsoleBean;
import it.unisa.Model.AccessorioBean;
import it.unisa.Model.GiocoModelDS;
import it.unisa.Model.ConsoleModelDS;
import it.unisa.Model.AccessorioModelDS;

public class CartControl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
			HttpSession session = request.getSession();
			Cart cart = (Cart) session.getAttribute("cart");
			if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
			}
			
			String action   = request.getParameter("action");
			String codeStr  = request.getParameter("id");
			String qtyStr   = request.getParameter("quantity");
			String prefisso = request.getParameter("prefisso");
			
			int code     = codeStr  != null ? Integer.parseInt(codeStr) : -1;
			int quantity = qtyStr   != null ? Integer.parseInt(qtyStr)  :  0;
			
			try {
			switch (action) {
			   case "deleteC":
			       cart.updateProductQuantity(code, prefisso, 0);
			       break;
			
			   case "updateC":
			       int qtyRequest = quantity;
			       int available  = 0;
			       if ("G".equals(prefisso)) {
			           GiocoModelDS gm = new GiocoModelDS();
			           GiocoBean original = gm.doRetrieveByKey(code);
			           available = original != null ? original.getQuantity() : 0;
			       }
			       else if ("C".equals(prefisso)) {
			           ConsoleModelDS cm = new ConsoleModelDS();
			           ConsoleBean original = cm.doRetrieveByKey(code);
			           available = original != null ? original.getQuantity() : 0;
			       }
			       else if ("A".equals(prefisso)) {
			           AccessorioModelDS am = new AccessorioModelDS();
			           AccessorioBean original = am.doRetrieveByKey(code);
			           available = original != null ? original.getQuantity() : 0;
			       }
			
			       int finalQty = Math.min(qtyRequest, available);
			       if (qtyRequest > available) {
			           session.setAttribute("cartError",
			               "Quantità massima disponibile per “" + code
			               + "” è " + available + ". Ho regolato a " + finalQty + ".");
			       } else {
			           session.removeAttribute("cartError");
			       }
			
			       cart.updateProductQuantity(code, prefisso, finalQty);
			       break;
			}
			} catch (SQLException e) {
			throw new ServletException("Errore durante il recupero dello stock", e);
			}
			
			session.setAttribute("cart", cart);
			response.sendRedirect(request.getContextPath() + "/CartView.jsp");
			}


    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
                         throws ServletException, IOException {
        doGet(req, resp);
    }
}
