package com.bloomie.platform.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "ChangePasswordRequest", description = "Request payload for changing a user's password")
public record ChangePasswordResource(
        @NotBlank(message = "{user.password.blank}")
        @Schema(description = "Current password", example = "secret123")
        String currentPassword,

        @NotBlank(message = "{user.password.blank}")
        @Schema(description = "New password", example = "newSecret456")
        String newPassword) {}
