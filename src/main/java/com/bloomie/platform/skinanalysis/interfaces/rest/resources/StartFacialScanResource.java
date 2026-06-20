package com.bloomie.platform.skinanalysis.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * Request body for starting a facial scan session.
 *
 * @param patientId the IAM user id of the patient starting the scan
 */
@Schema(description = "Request body to start a facial scan for a patient")
public record StartFacialScanResource(
        @NotNull @Schema(example = "1") Long patientId) {
}
