package com.bloomie.platform.dermatologyCare.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * Request DTO for updating a {@code DermatologistProfile}.
 */
@Schema(name = "UpdateDermatologistProfileRequest", description = "Request payload for updating a dermatologist profile")
public record UpdateDermatologistProfileResource(
        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "First name", example = "Carlos")
        String firstName,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Last name", example = "Pérez")
        String lastName,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Medical specialty", example = "Dermatología clínica")
        String specialty,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "License number", example = "CMP-12345")
        String licenseNumber,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Contact phone (digits only, 7-15 chars)", example = "987654321")
        String phone,

        @Schema(description = "Professional biography")
        String biography) {
}
