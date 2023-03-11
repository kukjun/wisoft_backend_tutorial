package io.wisoft.tutorial_backend.interceptor;

import io.wisoft.tutorial_backend.handler.exception.interceptor.AuthenticationException;
import io.wisoft.tutorial_backend.util.jwt.JwtProvider;
import io.wisoft.tutorial_backend.util.jwt.JwtCommunicationServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
@RequiredArgsConstructor
public class VerifyInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(VerifyInterceptor.class);

    private final JwtProvider jwtProvider;
    private final JwtCommunicationServlet jwtCommunicationServlet;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        logger.info("user verify interceptor preHandle execute");
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        String token = jwtCommunicationServlet.extract(request);
        logger.debug("token: " + token);
        boolean verifyToken = jwtProvider.verifyToken(token);
        logger.debug("verifyToken: " + verifyToken);

        if (!verifyToken) {
            throw new AuthenticationException("Auth Fail: it was expired or inner exception occur");
        }

        String refreshToken = jwtProvider.refreshToken(token);
        jwtCommunicationServlet.insert(response, refreshToken);
        logger.debug("response.getHeader() = " + response.getHeader("Authorization").isEmpty());

        return true;

    }

}
