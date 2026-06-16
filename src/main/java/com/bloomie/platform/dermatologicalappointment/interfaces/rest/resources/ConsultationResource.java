package com.bloomie.platform.dermatologicalappointment.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Clinical consultation response")
public record ConsultationResource(
        @Schema(example = "1") Long id,
        @Schema(example = "1") Long appointmentId,
        @Schema(example = "1") Long patientId,
        @Schema(example = "2") Long dermatologistId,
        @Schema(example = "IN_PROGRESS") String status,
        @Schema(example = "Mild rosacea") String notes,
        @Schema(example = "Apply metronidazole gel") String recommendations,
        List<String> clinicalPhotoUrls,
        @Schema(example = "2025-12-25T10:05:00") String startedAt,
        @Schema(example = "2025-12-25T10:35:00") String finishedAt) {
}
