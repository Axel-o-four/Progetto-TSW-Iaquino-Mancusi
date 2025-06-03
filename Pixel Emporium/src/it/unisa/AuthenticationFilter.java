package it.unisa;

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
        HttpSession session = req.getSession(false);
         
        if (session == null || session.getAttribute("user") == null) {
            ((HttpServletResponse) response).sendRedirect(req.getContextPath() + "/invalidLogin.jsp");
        } else {
            chain.doFilter(request, response);
        }
    }
}
