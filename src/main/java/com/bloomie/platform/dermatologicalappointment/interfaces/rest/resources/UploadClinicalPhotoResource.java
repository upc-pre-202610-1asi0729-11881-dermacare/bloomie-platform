package com.bloomie.platform.dermatologicalappointment.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request body to upload a clinical photo")
public record UploadClinicalPhotoResource(
        @NotBlank @Schema(example = "https://storage.example.com/photos/derma-001.jpg") String photoUrl) {
}
