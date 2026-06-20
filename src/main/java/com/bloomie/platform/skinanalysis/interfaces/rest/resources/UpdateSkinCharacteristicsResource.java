package com.bloomie.platform.skinanalysis.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * Request body for updating the skin characteristics of an existing skin profile.
 *
 * @param skinType    skin type label (NORMAL | DRY | OILY | COMBINATION | SENSITIVE)
 * @param sensitivity sensitivity level (LOW | MEDIUM | HIGH)
 * @param waterIntake daily water intake range (e.g. "3-5 glasses")
 * @param sunExposure daily sun exposure range (e.g. "30-60 minutes")
 * @param sleepHours  daily sleep range (e.g. "8 hours")
 */
@Schema(description = "Request body to update the skin characteristics of a skin profile")
public record UpdateSkinCharacteristicsResource(
        @NotBlank @Schema(example = "DRY")            String skinType,
        @NotBlank @Schema(example = "HIGH")           String sensitivity,
        @NotBlank @Schema(example = "6-8 glasses")    String waterIntake,
        @NotBlank @Schema(example = "1-2 hours")      String sunExposure,
        @NotBlank @Schema(example = "6-7 hours")      String sleepHours) {
}
