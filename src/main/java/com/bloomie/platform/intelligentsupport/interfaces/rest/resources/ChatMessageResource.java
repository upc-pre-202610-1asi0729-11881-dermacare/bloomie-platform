package com.bloomie.platform.intelligentsupport.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Chat message response")
public record ChatMessageResource(
        @Schema(example = "1")                          Long id,
        @Schema(example = "1")                          Long supportQueryId,
        @Schema(example = "How do I control oily skin?") String text,
        @Schema(example = "AI")                         String messageType,
        @Schema(example = "2026-06-28T10:00:00")        String sentAt
) {}