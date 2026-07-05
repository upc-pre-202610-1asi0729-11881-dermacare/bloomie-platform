package com.bloomie.platform.iam.infrastructure.tokens.jwt.services;

import com.bloomie.platform.iam.infrastructure.tokens.jwt.BearerTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

/**
 * JWT implementation of {@link BearerTokenService}.
 *
 * <p>Generates HMAC-SHA signed JWTs using the secret and expiration configured via
 * {@code authorization.jwt.secret} and {@code authorization.jwt.expiration.days}
 * in {@code application.properties}. In bloomie, the token <em>subject</em> is the
 * user's email address (the unique identifier used throughout the platform).</p>
 */
@Service
@Slf4j
public class TokenServiceImpl implements BearerTokenService {

    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String BEARER_TOKEN_PREFIX = "Bearer ";
    private static final int BEARER_PREFIX_LENGTH = 7;

    @Value("${authorization.jwt.secret}")
    private String secret;

    @Value("${authorization.jwt.expiration.days}")
    private int expirationDays;

    /**
     * Generates a token using the principal name from an {@link Authentication} object.
     *
     * @param authentication Spring Security authentication containing the principal name
     * @return signed JWT string
     */
    @Override
    public String generateToken(Authentication authentication) {
        return buildToken(authentication.getName());
    }

    /**
     * Generates a token for a given username (email in bloomie).
     *
     * @param username the email address used as the JWT subject
     * @return signed JWT string
     */
    @Override
    public String generateToken(String username) {
        return buildToken(username);
    }

    /**
     * Extracts the subject claim (email) from a token.
     *
     * @param token signed JWT string
     * @return email address embedded as the token subject
     */
    @Override
    public String getUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Validates the token signature, expiry, and structure.
     * Logs the specific cause of validation failures without throwing.
     *
     * @param token signed JWT string
     * @return {@code true} if the token is valid; {@code false} otherwise
     */
    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
            log.info("Token is valid");
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JSON Web Token signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JSON Web Token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JSON Web Token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JSON Web Token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JSON Web Token claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    /**
     * Extracts the raw bearer token from the {@code Authorization} header.
     *
     * @param request incoming HTTP request
     * @return raw token string without the {@code "Bearer "} prefix, or {@code null}
     */
    @Override
    public String getBearerTokenFrom(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER_NAME);
        if (isTokenPresentIn(header) && isBearerTokenIn(header)) {
            return extractTokenFrom(header);
        }
        return null;
    }

    // -- private helpers --

    private String buildToken(String username) {
        var issuedAt = new Date();
        var expiration = DateUtils.addDays(issuedAt, expirationDays);
        return Jwts.builder()
                .subject(username)
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private boolean isTokenPresentIn(String authorizationHeader) {
        return StringUtils.hasText(authorizationHeader);
    }

    private boolean isBearerTokenIn(String authorizationHeader) {
        return authorizationHeader.startsWith(BEARER_TOKEN_PREFIX);
    }

    private String extractTokenFrom(String authorizationHeader) {
        return authorizationHeader.substring(BEARER_PREFIX_LENGTH);
    }
}
