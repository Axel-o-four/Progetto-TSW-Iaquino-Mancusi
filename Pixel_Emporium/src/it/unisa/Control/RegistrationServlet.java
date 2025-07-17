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
 
public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    UserModel userModel = new UserModelDS();
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String dataNascitaStr = request.getParameter("dataNascita");
        String genere = request.getParameter("genere");
        String indirizzo = request.getParameter("indirizzo");
        String citta = request.getParameter("citta");
        String prov = request.getParameter("prov");
        String cap = request.getParameter("cap");
        String passwordRaw = request.getParameter("password");
        
        String hashedPassword = PasswordUtil.hashPassword(passwordRaw);
        
        UserBean user = new UserBean();
        user.setEmail(email);
        user.setNome(nome);
        user.setCognome(cognome);
        try {
            java.sql.Date dataNascita = java.sql.Date.valueOf(dataNascitaStr);
            user.setDataNascita(dataNascita);
        } catch (IllegalArgumentException e) {
            request.setAttribute("message", "Formato data non valido. Usa yyyy-MM-dd.");
            RequestDispatcher rd = request.getRequestDispatcher("registration.jsp");
            rd.forward(request, response);
            return;
        }
        user.setGenere(genere);
        user.setIndirizzo(indirizzo);
        user.setCitta(citta);
        user.setProv(prov);
        user.setCap(cap);
        user.setPassword(hashedPassword);
        
        try {
            UserBean existingUser = userModel.doRetrieveByKey(email);
            if(existingUser != null) {
                request.setAttribute("message", "Email già registrata, effettua il login.");
                RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                rd.forward(request, response);
            } else {
                userModel.doSave(user);
                request.setAttribute("message", "Registrazione avvenuta con successo, effettua il login.");
                RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                rd.forward(request, response);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            request.setAttribute("message", "Errore durante la registrazione: " + ex.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("registration.jsp");
            rd.forward(request, response);
        }
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
