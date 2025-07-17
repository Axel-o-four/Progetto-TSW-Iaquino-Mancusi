package it.unisa.Control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import it.unisa.Model.UserBean;
import it.unisa.Model.UserModel;
import it.unisa.Model.UserModelDS;

public class UserAdminControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static final UserModel model = new UserModelDS();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Collection<UserBean> allUsers = model.doRetrieveAll(null);
            req.setAttribute("users", allUsers);
        } catch (SQLException e) {
            throw new ServletException("Errore nel recupero utenti", e);
        }
        
        RequestDispatcher rd = req.getRequestDispatcher("/admin/UserList.jsp");
        rd.forward(req, resp);
    }
}
