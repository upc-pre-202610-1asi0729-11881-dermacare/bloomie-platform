package com.bloomie.platform.intelligentsupport.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response representation of a support query session.
 *
 * @param id              the support query id
 * @param patientId       the IAM user id of the patient
 * @param skinProfileId   the id of the patient's skin profile
 * @param status          the current lifecycle status
 * @param suggestedAction the AI-recommended action
 * @param createdAt       the creation timestamp
 */
@Schema(description = "Support query session response")
public record SupportQueryResource(
        @Schema(example = "1")                                    Long id,
        @Schema(example = "1")                                    Long patientId,
        @Schema(example = "1")                                    Long skinProfileId,
        @Schema(example = "IN_PROGRESS")                          String status,
        @Schema(example = "CONTINUE_ROUTINE")                     String suggestedAction,
        @Schema(example = "2026-06-28T10:00:00")                  String createdAt
) {}