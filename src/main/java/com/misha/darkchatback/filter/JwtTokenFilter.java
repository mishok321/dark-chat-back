package com.misha.darkchatback.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.misha.darkchatback.exception.InvalidJwtAuthenticationException;
import com.misha.darkchatback.security.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class JwtTokenFilter extends GenericFilterBean {
    public static final String ACCESS_CONTROL_ALLOW_ORIGIN_HEADER = "Access-Control-Allow-Origin";
    public static final String ACCESS_CONTROL_ALLOW_ORIGIN_VALUE = "*";
    public static final String ACCESS_CONTROL_ALLOW_HEADERS_HEADER = "Access-Control-Allow-Headers";
    public static final String ACCESS_CONTROL_ALLOW_HEADERS_VALUE = "Authorization, Content-Type";
    public static final String RESPONSE_CONTENT_TYPE = "application/json";
    public static final String RESPONSE_ENCODING = "UTF-8";
    public static final String TIMESTAMP_FIELD = "timestamp";
    public static final String STATUS_FIELD = "status";
    public static final String ERRORS_FIELD = "errors";
    @Value("${chat.development-mode}")
    private boolean isDevelopmentMode;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (InvalidJwtAuthenticationException e) {
            final ObjectMapper objectMapper = new ObjectMapper();
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            if (isDevelopmentMode) {
                response.setHeader(
                        ACCESS_CONTROL_ALLOW_ORIGIN_HEADER,
                        ACCESS_CONTROL_ALLOW_ORIGIN_VALUE
                );
                response.setHeader(
                        ACCESS_CONTROL_ALLOW_HEADERS_HEADER,
                        ACCESS_CONTROL_ALLOW_HEADERS_VALUE
                );
            }
            response.setContentType(RESPONSE_CONTENT_TYPE);
            response.setCharacterEncoding(RESPONSE_ENCODING);
            response.setStatus(HttpServletResponse.SC_OK);
            Map<String, Object> body = new HashMap<>();
            body.put(TIMESTAMP_FIELD, LocalDateTime.now().toString());
            body.put(STATUS_FIELD, HttpServletResponse.SC_UNAUTHORIZED);
            body.put(ERRORS_FIELD, List.of("Token is not valid"));
            String jsonBody = objectMapper.writeValueAsString(body);
            response.getOutputStream().write(jsonBody.getBytes());
        }
    }
}
