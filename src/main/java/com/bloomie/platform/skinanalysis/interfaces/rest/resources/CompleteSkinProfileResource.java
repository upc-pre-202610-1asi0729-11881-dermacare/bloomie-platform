package com.bloomie.platform.skinanalysis.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Request body for completing a patient's skin profile.
 *
 * @param patientId   the IAM user id of the patient
 * @param skinType    skin type label (NORMAL | DRY | OILY | COMBINATION | SENSITIVE)
 * @param sensitivity sensitivity level (LOW | MEDIUM | HIGH)
 * @param waterIntake daily water intake range (e.g. "3-5 glasses")
 * @param sunExposure daily sun exposure range (e.g. "30-60 minutes")
 * @param sleepHours  daily sleep range (e.g. "8 hours")
 */
@Schema(description = "Request body to complete a patient's skin profile")
public record CompleteSkinProfileResource(
        @NotNull  @Schema(example = "1")              Long patientId,
        @NotBlank @Schema(example = "OILY")           String skinType,
        @NotBlank @Schema(example = "MEDIUM")         String sensitivity,
        @NotBlank @Schema(example = "3-5 glasses")    String waterIntake,
        @NotBlank @Schema(example = "30-60 minutes")  String sunExposure,
        @NotBlank @Schema(example = "8 hours")        String sleepHours) {
}
