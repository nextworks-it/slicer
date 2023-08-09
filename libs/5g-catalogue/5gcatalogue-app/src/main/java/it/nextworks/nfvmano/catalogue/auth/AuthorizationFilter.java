package it.nextworks.nfvmano.catalogue.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        final String val = req.getHeader("Authorization");
        log.debug("Authorization header : " + val);
        if (val == null || val.length() == 0) {
            //((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "The 'Authorization' header is missing. Please provide a valid token");
            log.error("The 'Authorization' header is missing. Please provide a valid bearer token");
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ((HttpServletResponse) response).getWriter().print("The 'Authorization' header is missing. Please provide a valid bearer token");
        } else if (!val.matches("Bearer (.+)")) {
            log.error("Token format is not valid. Please provide a valid bearer token");
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ((HttpServletResponse) response).getWriter().print("Token format is not valid. Please provide a valid bearer token");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
