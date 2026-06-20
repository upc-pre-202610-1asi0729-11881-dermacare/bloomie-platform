package com.bloomie.platform.dermatologycare.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response DTO for an {@code Availability} slot.
 */
@Schema(name = "Availability", description = "Availability slot response")
public record AvailabilityResource(
        @Schema(description = "Availability id") Long id,
        @Schema(description = "IAM user id of the dermatologist") Long dermatologistId,
        @Schema(description = "Day of the week", example = "MONDAY") String day,
        @Schema(description = "Start time (HH:mm)", example = "09:00") String startTime,
        @Schema(description = "End time (HH:mm)", example = "17:00") String endTime,
        @Schema(description = "Whether this slot is active") boolean active) {
}
