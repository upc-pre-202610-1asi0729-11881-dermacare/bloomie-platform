package com.bloomie.platform.dermatologicalappointment.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request body to submit a reprogram request")
public record ReprogramRequestResource(
        @NotNull @Schema(example = "1") Long patientId,
        @NotBlank @Schema(example = "2025-12-30T10:00:00") String newDate) {
}
