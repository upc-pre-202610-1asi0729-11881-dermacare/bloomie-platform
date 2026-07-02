package com.bloomie.platform.dermatologycare.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Request resource for defining a new {@code Availability} slot.
 */
@Schema(name = "DefineAvailabilityRequest", description = "Request payload for defining an availability slot")
public record DefineAvailabilityResource(
        @NotNull(message = "{validation.not-blank}")
        @Positive
        @Schema(description = "IAM user id of the dermatologist", example = "1")
        Long dermatologistId,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Day of the week", example = "MONDAY")
        String day,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "Start time (HH:mm)", example = "09:00")
        String startTime,

        @NotBlank(message = "{validation.not-blank}")
        @Schema(description = "End time (HH:mm)", example = "17:00")
        String endTime) {
}
