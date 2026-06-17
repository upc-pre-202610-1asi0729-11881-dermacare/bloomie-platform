package com.bloomie.platform.skinanalysis.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response representation of a facial scan.
 */
@Schema(description = "Facial scan response")
public record FacialScanResource(
        @Schema(example = "1")                       Long id,
        @Schema(example = "1")                       Long patientId,
        @Schema(example = "STARTED")                 String status,
        @Schema(example = "null")                    String photoUrl,
        @Schema(example = "2026-06-17T10:00:00")     String scannedAt) {
}
