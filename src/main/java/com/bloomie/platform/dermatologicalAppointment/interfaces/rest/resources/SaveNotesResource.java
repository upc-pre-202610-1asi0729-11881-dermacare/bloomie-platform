package com.bloomie.platform.dermatologicalAppointment.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body to save clinical notes progressively")
public record SaveNotesResource(
        @Schema(example = "Patient presents mild rosacea on cheeks") String notes) {
}
