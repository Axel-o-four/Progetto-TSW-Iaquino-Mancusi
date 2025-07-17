package it.unisa.Control;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
 
@WebFilter("/userlogged/*")
public class AuthenticationFilter implements Filter {
    public void init(FilterConfig fConfig) throws ServletException { }
    public void destroy() { }
     
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();

        if (uri.endsWith("/userlogged/invalidLogin.jsp")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/userlogged/invalidLogin.jsp");
        } else {
            chain.doFilter(request, response);
        }
    }

}
