package it.unisa.Control;

import it.unisa.Model.UserModel;
import it.unisa.Model.UserModelDS;
import it.unisa.Model.UserBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

public class CheckEmailServlet extends HttpServlet {
    private final UserModel userModel = new UserModelDS();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        boolean exists = false;

        try {
            UserBean user = userModel.doRetrieveByKey(email);
            exists = userModel.emailExists(email);
        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write("{\"exists\":" + exists + "}");
    }
}
