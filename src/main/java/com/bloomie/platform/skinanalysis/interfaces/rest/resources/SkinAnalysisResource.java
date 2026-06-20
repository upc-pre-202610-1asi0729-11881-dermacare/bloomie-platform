package com.bloomie.platform.skinanalysis.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response representation of a skin analysis result.
 */
@Schema(description = "Skin analysis response")
public record SkinAnalysisResource(
        @Schema(example = "1")                       Long id,
        @Schema(example = "1")                       Long patientId,
        @Schema(example = "1")                       Long facialScanId,
        @Schema(example = "65.0")                    Double overallScore,
        @Schema(example = "60.0")                    Double hydrationScore,
        @Schema(example = "65.0")                    Double textureScore,
        @Schema(example = "55.0")                    Double sensitivityScore,
        @Schema(example = "70.0")                    Double brightnessScore,
        @Schema(example = "COMPLETED")               String status,
        @Schema(example = "2026-06-17T10:00:00")     String analyzedAt) {
}
