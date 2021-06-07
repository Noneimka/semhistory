package history.filter;

import history.model.User;
import history.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@Component
@WebFilter(filterName = "dataFilter" ,urlPatterns = "/*")

public class DataOnAllPagesFilter implements Filter {

    private final UserService userService;

    @Autowired
    public DataOnAllPagesFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        Principal user = req.getUserPrincipal();
        if (user != null) {
            User currentUser = userService.getByEmail(user.getName());
            req.getSession().setAttribute("currentUser", currentUser);
        } else {
            req.getSession().setAttribute("currentUser", null);
        }

        filterChain.doFilter(req,resp);
    }
    @Override
    public void destroy() {

    }
}
