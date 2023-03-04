package io.wisoft.tutorial_backend.interceptor;

import io.wisoft.tutorial_backend.handler.AuthenticationException;
import io.wisoft.tutorial_backend.util.jwt.JwtProvider;
import io.wisoft.tutorial_backend.util.jwt.JwtTokenExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class JwtTokenVerifyInterceptor implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(JwtTokenVerifyInterceptor.class);
    private final JwtProvider jwtProvider;
    private final JwtTokenExtractor jwtTokenExtractor;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        logger.info("interceptor preHandle execute");
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        String token = jwtTokenExtractor.extract(request);
        logger.debug("token: " + token);
        boolean verifyToken = jwtProvider.verifyToken(token);
        logger.debug("verifyToken: " + verifyToken);

        if (!verifyToken) {
            throw new AuthenticationException("Auth Fail: it was expired or inner exception occur");
        }

        return true;

    }
}
