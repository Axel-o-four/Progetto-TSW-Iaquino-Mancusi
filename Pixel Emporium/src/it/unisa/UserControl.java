package it.unisa;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserControl extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    static boolean isDataSource = true;
    static UserModel model;
    
    static {
        if (isDataSource) {
            model = new UserModelDS();
        } else {
            model = new UserModelDM();
        }
    }
    
    public UserControl() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action != null) {
                if (action.equalsIgnoreCase("register")) {
                    String email = request.getParameter("email");
                    String nome = request.getParameter("nome");
                    String cognome = request.getParameter("cognome");
                    String dataNascitaStr = request.getParameter("dataNascita"); // atteso formato "yyyy-MM-dd"
                    String genere = request.getParameter("genere");
                    String indirizzo = request.getParameter("indirizzo");
                    String citta = request.getParameter("citta");
                    String prov = request.getParameter("prov");
                    String cap = request.getParameter("cap");
                    String password = request.getParameter("password");
                    
                    java.sql.Date dataNascita = null;
                    try {
                        dataNascita = java.sql.Date.valueOf(dataNascitaStr);
                    } catch (IllegalArgumentException e) {
                        request.setAttribute("message", "Formato data non valido. Usare yyyy-MM-dd.");
                        RequestDispatcher rd = request.getRequestDispatcher("/UserRegistration.jsp");
                        rd.forward(request, response);
                        return;
                    }
                    
                    UserBean user = new UserBean();
                    user.setEmail(email);
                    user.setNome(nome);
                    user.setCognome(cognome);
                    user.setDataNascita(dataNascita);
                    user.setGenere(genere);
                    user.setIndirizzo(indirizzo);
                    user.setCitta(citta);
                    user.setProv(prov);
                    user.setCap(cap);
                    user.setPassword(password);
                    
                    model.doSave(user);
                    request.setAttribute("message", "Registrazione eseguita con successo per l'utente " + email);
                } else if (action.equalsIgnoreCase("delete")) {
                    String email = request.getParameter("email");
                    boolean deleted = model.doDelete(email);
                    request.setAttribute("message", deleted ? "Utente eliminato con successo." : "Eliminazione fallita per l'utente " + email);
                } else if (action.equalsIgnoreCase("read")) {
                    String email = request.getParameter("email");
                    UserBean user = model.doRetrieveByKey(email);
                    request.setAttribute("user", user);
                    RequestDispatcher rd = request.getRequestDispatcher("/UserDetail.jsp");
                    rd.forward(request, response);
                    return;
                }
            }
            
            String sort = request.getParameter("sort");
            Collection<UserBean> users = model.doRetrieveAll(sort);
            request.setAttribute("users", users);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Errore SQL: " + e.getMessage());
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/UserList.jsp");
        dispatcher.forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
