package io.wisoft.tutorial_backend.util.jwt;

import io.wisoft.tutorial_backend.handler.exception.interceptor.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class JwtCommunicationServlet {

    private final String authorizationHeaderPrefix;
    private final String bearerTypePrefix;
    private final Logger logger = LoggerFactory.getLogger(JwtCommunicationServlet.class);

    public JwtCommunicationServlet(
            @Value("${jwt.auth-header}") String authorizationHeaderPrefix,
            @Value("${jwt.bearer-prefix}") String bearerTypePrefix
    ) {
        this.authorizationHeaderPrefix = authorizationHeaderPrefix;
        this.bearerTypePrefix = bearerTypePrefix;
    }

    public String extract(HttpServletRequest request) {
        String header = request.getHeader(authorizationHeaderPrefix);
        if (header == null) {
            logger.debug("Header not found");
            throw new AuthenticationException("Auth Fail: Not Found Header");
        } else if (header.startsWith(bearerTypePrefix)) {
            logger.debug("Header type correct");
            return header.substring(bearerTypePrefix.length());
        } else {
            logger.debug("Header auth type mismatch");
            throw new AuthenticationException("Auth Fail: Auth Type Mismatch");
        }
    }

    public void insert(HttpServletResponse response, String token) {
        response.setHeader(authorizationHeaderPrefix, bearerTypePrefix + token);
    }
}
