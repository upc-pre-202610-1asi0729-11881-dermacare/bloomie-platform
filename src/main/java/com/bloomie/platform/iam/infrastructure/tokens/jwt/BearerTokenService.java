package com.bloomie.platform.iam.infrastructure.tokens.jwt;

import com.bloomie.platform.iam.application.internal.outboundservices.tokens.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

/**
 * Marker interface for the JWT bearer-token service.
 *
 * <p>Extends {@link TokenService} with HTTP-request extraction and
 * {@link Authentication}-based generation. The concrete implementation is
 * {@link com.bloomie.platform.iam.infrastructure.tokens.jwt.services.TokenServiceImpl}.</p>
 */
public interface BearerTokenService extends TokenService {

    /**
     * Extracts the bearer token from the {@code Authorization} header of the given request.
     *
     * @param request incoming HTTP request
     * @return raw token string, or {@code null} if no valid bearer token is present
     */
    String getBearerTokenFrom(HttpServletRequest request);

    /**
     * Generates a signed token from a Spring Security {@link Authentication} object.
     *
     * @param authentication authenticated principal
     * @return signed token string
     */
    String generateToken(Authentication authentication);
}
