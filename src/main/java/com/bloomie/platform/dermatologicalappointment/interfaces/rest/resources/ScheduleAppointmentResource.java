package com.bloomie.platform.dermatologicalappointment.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request body for scheduling a new dermatological appointment.
 *
 * @param patientId       the IAM user id of the patient
 * @param dermatologistId the IAM user id of the chosen dermatologist
 * @param scheduledAt     ISO-8601 date-time string; must represent a future instant
 */
@Schema(description = "Request body to schedule a new appointment")
public record ScheduleAppointmentResource(
        @NotNull @Schema(example = "1") Long patientId,
        @NotNull @Schema(example = "2") Long dermatologistId,
        @NotBlank @Schema(example = "2025-12-25T10:00:00") String scheduledAt) {
}
