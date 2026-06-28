package com.bloomie.platform.intelligentsupport.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request body to update the status of a support query")
public record UpdateSupportQueryStatusResource(
        @NotBlank @Schema(example = "RESOLVED") String status
) {}