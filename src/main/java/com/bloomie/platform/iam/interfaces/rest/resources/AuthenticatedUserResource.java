package com.bloomie.platform.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response returned after a successful sign-in.
 *
 * <p>Contains the user's identifier, email address, and the JWT bearer token
 * that must be included in the {@code Authorization} header of subsequent requests.</p>
 */
@Schema(
        name = "AuthenticatedUserResponse",
        description = "Authenticated user information with JWT token",
        example = "{\"id\": 1, \"email\": \"lucia@example.com\", \"token\": \"eyJhbGciOiJIUzI1NiIs...\"}"
)
public record AuthenticatedUserResource(
        @Schema(description = "Unique user identifier", example = "1")
        Long id,

        @Schema(description = "User email address (principal identifier)", example = "lucia@example.com")
        String email,

        @Schema(description = "JWT Bearer token for subsequent authenticated requests",
                example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String token
) {
}
