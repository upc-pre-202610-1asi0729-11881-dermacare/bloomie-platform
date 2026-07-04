package com.bloomie.platform.dermatologicalappointment.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dermatological appointment response")
public record AppointmentResource(
        @Schema(example = "1") Long id,
        @Schema(example = "1") Long patientId,
        @Schema(example = "2") Long dermatologistId,
        @Schema(example = "2025-12-25T10:00:00") String scheduledAt,
        @Schema(example = "SCHEDULED") String status,
        @Schema(example = "Personal emergency") String cancellationReason) {
}
