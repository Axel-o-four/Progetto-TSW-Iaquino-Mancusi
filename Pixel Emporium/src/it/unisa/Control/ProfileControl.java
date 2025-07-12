package it.unisa.Control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import it.unisa.Model.UserBean;
import it.unisa.Model.UserModel;
import it.unisa.Model.UserModelDS;

public class ProfileControl extends HttpServlet {
    private static final long serialVersionUID = 1L;

    static UserModel model = new UserModelDS();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/UserProfile.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        UserBean user = (UserBean) session.getAttribute("user");
        
        List<String> errorMessages = new ArrayList<>();
        
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String dataNascitaStr = request.getParameter("dataNascita");
        String genere = request.getParameter("genere");
        String indirizzo = request.getParameter("indirizzo");
        String citta = request.getParameter("citta");
        String prov = request.getParameter("prov");
        String cap = request.getParameter("cap");
        String newPassword = request.getParameter("password");

        if (nome == null || nome.trim().isEmpty()) {
            errorMessages.add("Il nome è obbligatorio.");
        }
        if (cognome == null || cognome.trim().isEmpty()) {
            errorMessages.add("Il cognome è obbligatorio.");
        }
        
        if (dataNascitaStr != null && !dataNascitaStr.isEmpty()) {
            try {
                java.sql.Date dataNascita = java.sql.Date.valueOf(dataNascitaStr);
                user.setDataNascita(dataNascita);
            } catch (IllegalArgumentException e) {
                errorMessages.add("Formato data non valido. Utilizzare 'yyyy-MM-dd'.");
            }
        } else {
            errorMessages.add("La data di nascita è obbligatoria.");
        }
        
        if (newPassword != null && !newPassword.trim().isEmpty()) {
            if (newPassword.length() < 8) {
                errorMessages.add("La password deve avere almeno 8 caratteri.");
            }
            else {
                String hashedPassword = PasswordUtil.hashPassword(newPassword);
                user.setPassword(hashedPassword);
            }
        }

        if (!errorMessages.isEmpty()) {
            request.setAttribute("errorMessages", errorMessages);
            RequestDispatcher rd = request.getRequestDispatcher("/userlogged/UserProfile.jsp");
            rd.forward(request, response);
            return;
        }
        
        user.setNome(nome);
        user.setCognome(cognome);
        if (genere != null) user.setGenere(genere);
        if (indirizzo != null) user.setIndirizzo(indirizzo);
        if (citta != null) user.setCitta(citta);
        if (prov != null) user.setProv(prov);
        if (cap != null) user.setCap(cap);
        
        try {
            model.updateUser(user);
            boolean success = model.updateUser(user); 
            if(success) {
                session.setAttribute("user", user);
                request.setAttribute("message", "Profilo aggiornato con successo.");
            } else {
                errorMessages.add("Nessun record aggiornato.");
                request.setAttribute("errorMessages", errorMessages);
            }
        } catch(SQLException ex) {
            errorMessages.add("Errore durante l'aggiornamento del profilo: " + ex.getMessage());
            request.setAttribute("errorMessages", errorMessages);
        }

        
        RequestDispatcher rd = request.getRequestDispatcher("/userlogged/UserProfile.jsp");
        rd.forward(request, response);
    }
}
