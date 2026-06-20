package com.bloomie.platform.dermatologicalappointment.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request body to start a consultation")
public record StartConsultationResource(
        @NotNull @Schema(example = "1") Long appointmentId,
        @NotNull @Schema(example = "2") Long dermatologistId,
        @NotNull @Schema(example = "1") Long patientId) {
}
