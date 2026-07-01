package com.bloomie.platform.iam.infrastructure.authorization.sfs.configuration;

import com.bloomie.platform.iam.infrastructure.authorization.sfs.pipeline.BearerAuthorizationRequestFilter;
import com.bloomie.platform.iam.infrastructure.hashing.bcrypt.BCryptHashingService;
import com.bloomie.platform.iam.infrastructure.tokens.jwt.BearerTokenService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

/**
 * Spring Security configuration for the IAM bounded context.
 *
 * <p>Sets up a stateless JWT-based security pipeline:
 * <ol>
 *   <li>CORS enabled for all origins (frontend SPA)</li>
 *   <li>CSRF disabled — stateless API, no session cookies</li>
 *   <li>Session management set to STATELESS</li>
 *   <li>{@link BearerAuthorizationRequestFilter} validates the JWT on every request</li>
 *   <li>Authentication endpoints and Swagger UI are public; everything else requires a valid token</li>
 *   <li>{@link com.bloomie.platform.iam.infrastructure.authorization.sfs.pipeline.UnauthorizedRequestHandlerEntryPoint}
 *       returns HTTP 401 instead of redirecting to a login page</li>
 * </ol>
 * </p>
 */
@Configuration
@EnableMethodSecurity
public class WebSecurityConfiguration {

    private final UserDetailsService userDetailsService;
    private final BearerTokenService tokenService;
    private final BCryptHashingService hashingService;
    private final AuthenticationEntryPoint unauthorizedRequestHandler;

    public WebSecurityConfiguration(
            @Qualifier("defaultUserDetailsService") UserDetailsService userDetailsService,
            BearerTokenService tokenService,
            BCryptHashingService hashingService,
            AuthenticationEntryPoint unauthorizedRequestHandler) {
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
        this.hashingService = hashingService;
        this.unauthorizedRequestHandler = unauthorizedRequestHandler;
    }

    /**
     * Creates the JWT bearer-token filter registered in the security filter chain.
     *
     * @return configured {@link BearerAuthorizationRequestFilter}
     */
    @Bean
    public BearerAuthorizationRequestFilter authorizationRequestFilter() {
        return new BearerAuthorizationRequestFilter(tokenService, userDetailsService);
    }

    /**
     * Exposes the {@link AuthenticationManager} from the auto-configured
     * {@link AuthenticationConfiguration} so it can be injected into other beans.
     *
     * @param authenticationConfiguration Spring auto-configuration holder
     * @return the application-level authentication manager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Creates a DAO-based authentication provider backed by the user-details service
     * and the BCrypt password encoder.
     *
     * @return configured {@link DaoAuthenticationProvider}
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(hashingService);
        return provider;
    }

    /**
     * Prevents Spring Boot from auto-registering {@link BearerAuthorizationRequestFilter}
     * as a plain servlet filter. It must only live inside the Spring Security filter chain
     * (added via {@code addFilterBefore}); double-registration would cause it to run twice
     * and interfere with {@code SecurityContextHolderFilter}.
     *
     * @param filter the JWT bearer filter bean
     * @return a disabled registration bean so Spring Boot skips the auto-registration
     */
    @Bean
    public FilterRegistrationBean<BearerAuthorizationRequestFilter> bearerFilterRegistration(
            BearerAuthorizationRequestFilter filter) {
        var registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }

    /**
     * Defines the JWT-protected security filter chain.
     *
     * <p>Public paths (authentication endpoints and Swagger UI) are permitted without a token;
     * all other paths require a valid bearer token in the {@code Authorization} header.</p>
     *
     * <p>No explicit {@code @Bean PasswordEncoder} is needed here because
     * {@link com.bloomie.platform.iam.infrastructure.hashing.bcrypt.services.HashingServiceImpl}
     * already satisfies the {@link org.springframework.security.crypto.password.PasswordEncoder}
     * contract via {@link BCryptHashingService}, avoiding a duplicate-bean conflict.</p>
     *
     * @param http Spring Security HTTP builder
     * @return the assembled {@link SecurityFilterChain}
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(configurer -> configurer.configurationSource(_ -> {
            var cors = new CorsConfiguration();
            cors.setAllowedOrigins(List.of("*"));
            cors.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
            cors.setAllowedHeaders(List.of("*"));
            return cors;
        }));

        http.csrf(csrfConfigurer -> csrfConfigurer.disable())
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(unauthorizedRequestHandler))
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/v1/authentication/**",
                                "/api/v1/plans/**",
                                "/api/v1/webhook/stripe",
                                "/api/v1/payments/checkout",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/swagger-resources/**",
                                "/webjars/**")
                        .permitAll()
                        .anyRequest().authenticated());

        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authorizationRequestFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
