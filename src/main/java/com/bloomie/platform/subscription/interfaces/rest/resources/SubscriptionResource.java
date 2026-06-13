package com.bloomie.platform.subscription.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "SubscriptionResponse",
        description = "Subscription information response",
        example = "{\"id\": 1, \"patientId\": 1, \"planId\": 2, \"status\": \"PENDING\"}")
public record SubscriptionResource(
        @Schema(description = "Subscription id", example = "1")
        Long id,

        @Schema(description = "Patient id", example = "1")
        Long patientId,

        @Schema(description = "Plan id", example = "1")
        Long planId,

        @Schema(description = "Subscription status", example = "PENDING")
        String status,

        @Schema(description = "Start date")
        LocalDateTime startDate,

        @Schema(description = "End date")
        LocalDateTime endDate) {}
