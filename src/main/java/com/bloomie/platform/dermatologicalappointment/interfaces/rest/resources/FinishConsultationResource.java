package com.bloomie.platform.dermatologicalappointment.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request body to finish a consultation")
public record FinishConsultationResource(
        @NotNull @Schema(example = "2") Long dermatologistId) {
}
