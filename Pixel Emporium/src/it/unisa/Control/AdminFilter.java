package it.unisa.Control;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import it.unisa.Model.UserBean;

@WebFilter("/admin/*")
public class AdminFilter implements Filter {
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest  request  = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;
    HttpSession session = request.getSession(false);

    UserBean user = session!=null
                  ? (UserBean) session.getAttribute("user")
                  : null;

    if (user==null || !user.isAdmin()) {
      response.sendRedirect(request.getContextPath() + "/accessDenied.jsp");
      return;
    }
    chain.doFilter(req, res);
  }
}
