package com.bloomie.platform.dermatologicalappointment.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body to record the final diagnosis and recommendations")
public record RecordDiagnosisResource(
        @Schema(example = "Mild rosacea, stage II") String notes,
        @Schema(example = "Apply metronidazole gel twice daily") String recommendations) {
}
