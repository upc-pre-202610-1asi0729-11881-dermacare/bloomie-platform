package com.bloomie.platform.intelligentsupport.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * Request body for creating a new intelligent support query session.
 *
 * @param patientId     the IAM user id of the patient
 * @param skinProfileId the id of the patient's skin profile
 */
@Schema(description = "Request body to create a new support query session")
public record CreateSupportQueryResource(
        @NotNull @Schema(example = "1") Long patientId,
        @NotNull @Schema(example = "1") Long skinProfileId
) {}