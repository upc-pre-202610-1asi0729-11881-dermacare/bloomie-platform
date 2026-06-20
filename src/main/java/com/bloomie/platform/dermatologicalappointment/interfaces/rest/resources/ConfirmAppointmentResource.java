package com.bloomie.platform.dermatologicalappointment.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request body to confirm an appointment")
public record ConfirmAppointmentResource(
        @NotNull @Schema(example = "1") Long patientId) {
}
