package com.bloomie.platform.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "SignInRequest", description = "Request payload for user sign-in")
public record SignInResource(
        @NotBlank(message = "{validation.not-blank}")
        @Email(message = "{validation.email}")
        @Schema(description = "Email address", example = "lucia@example.com")
        String email,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Password", example = "secret123")
        String password) {}