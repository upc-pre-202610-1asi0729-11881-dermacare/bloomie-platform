package com.bloomie.platform.skinanalysis.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * Request body for submitting a facial scan with a photo URL.
 *
 * @param photoUrl the URL of the uploaded photo
 */
@Schema(description = "Request body to submit a facial scan with a photo URL")
public record SubmitFacialScanResource(
        @NotBlank @Schema(example = "https://storage.example.com/scans/photo.jpg") String photoUrl) {
}
