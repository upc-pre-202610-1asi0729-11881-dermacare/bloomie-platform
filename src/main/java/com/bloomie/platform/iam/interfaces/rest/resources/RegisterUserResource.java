package com.bloomie.platform.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "RegisterUserRequest", description = "Request paylaod for registering a Young Adult user")
public record RegisterUserResource(
        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "First name", example = "Lucia")
        String firstName,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Last name", example = "Garcia")
        String lastName,

        @NotBlank(message = "{validation.not-blank}")
        @Email(message = "{validation.email}")
        @Schema(description = "Email address", example = "lucia@example.com")
        String email,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Password", example = "secret123")
        String password) {}
