package com.bloomie.platform.skinAnalysis.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Response representation of a completed skin profile.
 */
@Schema(description = "Skin profile response")
public record SkinProfileResource(
        @Schema(example = "1")                       Long id,
        @Schema(example = "1")                       Long patient_id,
        @Schema(example = "OILY")                    String skin_type,
        @Schema(example = "MEDIUM")                  String skin_tone,
        @Schema(example = "[\"ACNE\", \"PORES\"]")  List<String> concerns,
        @Schema(example = "COMPLETED")               String status) {
}
