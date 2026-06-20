package com.bloomie.platform.subscription.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(
        name = "PlanResponse",
        description = "Plan information response",
        example = "{\"id\": 1, \"type\": \"BASIC\", \"name\": \"Basic Plan\", \"price\": 9.99, \"durationDays\": 30, \"modules\": [\"SKIN_ANALYSIS\", \"ROUTINE_MANAGEMENT\"]}"
)
public record PlanResource(
        @Schema(description = "Plan unique identifier", example = "1")
        Long id,

        @Schema(description = "Plan type", example = "BASIC")
        String type,

        @Schema(description = "Plan name", example = "Basic Plan")
        String name,

        @Schema(description = "Plan price", example = "9.99")
        Double price,

        @Schema(description = "Duration in days", example = "30")
        Integer durationDays,

        @Schema(description = "Modules included in this plan", example = "[\"SKIN_ANALYSIS\", \"ROUTINE_MANAGEMENT\"]")
        List<String> modules) {}