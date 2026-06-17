package com.bloomie.platform.skinanalysis.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response representation of a skin profile.
 */
@Schema(description = "Skin profile response")
public record SkinProfileResource(
        @Schema(example = "1")               Long id,
        @Schema(example = "1")               Long patientId,
        @Schema(example = "OILY")            String skinType,
        @Schema(example = "MEDIUM")          String sensitivity,
        @Schema(example = "3-5 glasses")     String waterIntake,
        @Schema(example = "30-60 minutes")   String sunExposure,
        @Schema(example = "8 hours")         String sleepHours,
        @Schema(example = "COMPLETED")       String status) {
}
