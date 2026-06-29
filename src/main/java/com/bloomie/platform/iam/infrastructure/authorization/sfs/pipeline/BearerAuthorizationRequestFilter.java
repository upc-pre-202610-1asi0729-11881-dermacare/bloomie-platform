package com.bloomie.platform.iam.infrastructure.authorization.sfs.pipeline;

import com.bloomie.platform.iam.infrastructure.authorization.sfs.model.UsernamePasswordAuthenticationTokenBuilder;
import com.bloomie.platform.iam.infrastructure.tokens.jwt.BearerTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * HTTP filter that intercepts every request exactly once and, when a valid bearer
 * token is present, sets the authenticated principal in the {@link SecurityContextHolder}.
 *
 * <p>The filter is intentionally <em>not</em> annotated with {@code @Component} so that
 * Spring does not register it automatically in the generic filter chain — it is added
 * explicitly in {@link com.bloomie.platform.iam.infrastructure.authorization.sfs.configuration.WebSecurityConfiguration}.</p>
 */
@Slf4j
public class BearerAuthorizationRequestFilter extends OncePerRequestFilter {

    private final BearerTokenService tokenService;

    @Qualifier("defaultUserDetailsService")
    private final UserDetailsService userDetailsService;

    public BearerAuthorizationRequestFilter(BearerTokenService tokenService, UserDetailsService userDetailsService) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Extracts the bearer token from the request, validates it, and — if valid —
     * loads the user details and populates the Spring Security context.
     *
     * @param request     incoming HTTP request
     * @param response    outgoing HTTP response
     * @param filterChain remaining filter chain to call after processing
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = tokenService.getBearerTokenFrom(request);
            log.info("Token: {}", token);
            if (token != null && tokenService.validateToken(token)) {
                // Email is the JWT subject — used here as the Spring Security username
                String email = tokenService.getUsernameFromToken(token);
                var userDetails = userDetailsService.loadUserByUsername(email);
                SecurityContextHolder.getContext().setAuthentication(
                        UsernamePasswordAuthenticationTokenBuilder.build(userDetails, request));
            } else {
                log.info("No valid bearer token found in request");
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
