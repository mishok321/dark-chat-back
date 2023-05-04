package com.misha.darkchatback.redirect;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class RedirectFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        if ((req.getRequestURI().equals("/login") || req.getRequestURI().startsWith("/room"))
                && req.getMethod().equals("GET")) {
            resp.sendRedirect("/");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
