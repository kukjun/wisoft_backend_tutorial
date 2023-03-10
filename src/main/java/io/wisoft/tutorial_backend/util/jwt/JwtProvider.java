package io.wisoft.tutorial_backend.util.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.wisoft.tutorial_backend.domain.MemberRole;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    private final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    private final String secretKey;
    private final long tokenValidityInMilliseconds;
    private final String issuer;
    private final String subject;
    private final String audience;

    public JwtProvider(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds,
            @Value("${jwt.issuer}") String issuer,
            @Value("${jwt.subject}") String subject,
            @Value("${jwt.audience}") String audience
    ) {
        this.secretKey = secretKey;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
        this.issuer = issuer;
        this.subject = subject;
        this.audience = audience;

    }

    // jwt token 생성
    public String generateToken(long userId, String username, String memberRole) {
        return Jwts.builder()
                .setIssuer(issuer)
                .setSubject(subject)
                .setAudience(audience)
                .claim("userId", String.valueOf(userId))
                .claim("username", username)
                .claim("role", memberRole)
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidityInMilliseconds))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // jwt token 검증
    public boolean verifyToken(String token) {
        try {
            Claims claim = getAllClaims(token);
            return claim.getExpiration().after(new Date());
        } catch (JwtException e) {
            logger.debug("JWT Exception Occur: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e2) {
            logger.debug("IllegalArgumentException Occur: " + e2.getMessage());
            return false;
        }
    }

    // jwt token refresh
    public String refreshToken(String token) {
        Claims allClaims = getAllClaims(token);
        Long userId = Long.parseLong(allClaims.get("userId", String.class));
        String userName = allClaims.get("username", String.class);
        String role = allClaims.get("role", String.class);

        return generateToken(userId, userName, role);
    }

    // 모든 claim 조회
    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    // 명시적 claim 조회
    public String getClaims(String token, String key) {
        Claims claims = getAllClaims(token);
        return claims.get(key, String.class);
    }

}
