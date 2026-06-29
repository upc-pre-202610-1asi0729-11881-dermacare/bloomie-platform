package com.bloomie.platform.iam.infrastructure.authorization.sfs.pipeline;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Entry point invoked by Spring Security when an unauthenticated request
 * reaches a protected resource.
 *
 * <p>Returns HTTP 401 instead of the default redirect to a login page,
 * which is the correct behaviour for a stateless REST API.</p>
 */
@Component
@Slf4j
public class UnauthorizedRequestHandlerEntryPoint implements AuthenticationEntryPoint {

    /**
     * Sends an HTTP 401 Unauthorized response and logs the authentication failure.
     *
     * @param request                 the request that triggered the exception
     * @param response                the response used to send the 401 status
     * @param authenticationException the exception that caused the invocation
     */
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authenticationException) throws IOException, ServletException {
        log.error("Unauthorized request: {}", authenticationException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized request detected");
    }
}
