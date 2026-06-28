package com.bloomie.platform.intelligentsupport.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request body to send a chat message")
public record SendChatMessageResource(
        @NotNull  @Schema(example = "1")                    Long supportQueryId,
        @NotBlank @Schema(example = "How do I control oily skin?") String text
) {}