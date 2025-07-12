package it.unisa.Control;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import it.unisa.Model.UserBean;
import it.unisa.Model.UserModel;
import it.unisa.Model.UserModelDS;
 
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    UserModel userModel = new UserModelDS();
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String passwordRaw = request.getParameter("password");
        
        String hashedPassword = PasswordUtil.hashPassword(passwordRaw);
 
        try {
            UserBean user = userModel.doRetrieveByKey(email);
            if (user != null && user.getPassword().equals(hashedPassword)) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/CatalogView.jsp");
            } else {
                request.setAttribute("message", "Username e/o password errati.");
                RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                rd.forward(request, response);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("message", "Errore durante il login: " + ex.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
        }
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
