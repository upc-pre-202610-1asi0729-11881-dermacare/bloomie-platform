package com.bloomie.platform.skinAnalysis.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Request body for completing a patient's skin profile.
 *
 * @param patient_id  the IAM user id of the patient
 * @param skin_type   skin type label (OILY | DRY | MIXED | NORMAL | SENSITIVE)
 * @param skin_tone   skin tone label (FAIR | LIGHT | MEDIUM | OLIVE | DARK | DEEP)
 * @param concerns    non-empty list of skin concern labels
 */
@Schema(description = "Request body to complete a patient's skin profile")
public record CompleteSkinProfileResource(
        @NotNull  @Schema(example = "1")       Long patient_id,
        @NotBlank @Schema(example = "OILY")    String skin_type,
        @NotBlank @Schema(example = "MEDIUM")  String skin_tone,
        @NotEmpty @Schema(example = "[\"ACNE\", \"PORES\"]") List<String> concerns) {
}
