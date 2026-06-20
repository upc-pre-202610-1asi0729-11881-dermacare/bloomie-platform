package com.bloomie.platform.dermatologicalappointment.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request body to cancel an appointment")
public record CancelAppointmentResource(
        @NotNull @Schema(example = "1") Long patientId,
        @NotBlank @Schema(example = "Personal emergency") String cancellationReason) {
}
