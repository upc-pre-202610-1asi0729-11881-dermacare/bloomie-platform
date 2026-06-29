package com.bloomie.platform.iam.infrastructure.authorization.sfs.model;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

/**
 * Utility that builds a fully authenticated {@link UsernamePasswordAuthenticationToken}
 * from a loaded {@link UserDetails} and the current HTTP request.
 *
 * <p>Sets the request details on the token so that Spring Security audit events
 * and session management have access to IP address and session information.</p>
 */
public class UsernamePasswordAuthenticationTokenBuilder {

    /**
     * Creates a pre-authenticated token with the request's web authentication details.
     *
     * @param principal   loaded user details (email + roles)
     * @param request     current HTTP request used to populate web authentication details
     * @return ready-to-use authentication token (credentials are {@code null} post-auth)
     */
    public static UsernamePasswordAuthenticationToken build(UserDetails principal, HttpServletRequest request) {
        var token = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return token;
    }
}
