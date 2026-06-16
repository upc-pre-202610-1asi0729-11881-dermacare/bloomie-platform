package com.bloomie.platform.dermatologycare.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * Request DTO for updating an existing {@code Availability} slot.
 */
@Schema(name = "UpdateAvailabilityRequest", description = "Request payload for updating an availability slot")
public record UpdateAvailabilityResource(
        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Day of the week", example = "TUESDAY")
        String day,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Start time (HH:mm)", example = "10:00")
        String startTime,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "End time (HH:mm)", example = "18:00")
        String endTime) {
}
