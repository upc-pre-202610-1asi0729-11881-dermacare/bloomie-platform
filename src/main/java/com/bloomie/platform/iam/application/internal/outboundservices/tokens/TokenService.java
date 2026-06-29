package com.bloomie.platform.iam.application.internal.outboundservices.tokens;

/**
 * Outbound port for bearer token issuance and validation.
 *
 * <p>Decouples the application layer from the JWT library so the
 * infrastructure implementation can be swapped without touching domain logic.</p>
 */
public interface TokenService {

    /**
     * Generates a signed token for the given principal identifier.
     *
     * @param username the principal identifier embedded as the token subject
     * @return signed token string
     */
    String generateToken(String username);

    /**
     * Extracts the principal identifier (subject) from a token.
     *
     * @param token signed token string
     * @return username embedded in the token
     */
    String getUsernameFromToken(String token);

    /**
     * Validates the token signature, expiry, and structure.
     *
     * @param token signed token string
     * @return {@code true} when the token is valid; {@code false} otherwise
     */
    boolean validateToken(String token);
}
