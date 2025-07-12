package it.unisa.Control;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import it.unisa.Model.OrdineBean;
import it.unisa.Model.OrdineModel;
import it.unisa.Model.OrdineModelDS;

public class OrdersAdminControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static final OrdineModel model = new OrdineModelDS();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email   = req.getParameter("email");
        String fromStr = req.getParameter("from");
        String toStr   = req.getParameter("to");
        Date from = null, to = null;

        try {
            if (fromStr != null && !fromStr.isEmpty()) {
                from = Date.valueOf(fromStr);
            }
            if (toStr != null && !toStr.isEmpty()) {
                to = Date.valueOf(toStr);
            }

            Collection<OrdineBean> ordini;
            if (email != null && !email.isEmpty() && from != null && to != null) {
                ordini = model.doRetrieveByUserAndPeriod(email, from, to);
            } else if (from != null && to != null) {
                ordini = model.doRetrieveByPeriod(from, to);
            } else if (email != null && !email.isEmpty()) {
                ordini = model.doRetrieveByUser(email);
            } else {
                ordini = model.doRetrieveAll(null);
            }
            req.setAttribute("ordini", ordini);

        } catch (SQLException e) {
            throw new ServletException("Errore recupero ordini admin", e);
        }

        RequestDispatcher rd = req.getRequestDispatcher("/admin/OrderAdminView.jsp");
        rd.forward(req, resp);
    }
}
