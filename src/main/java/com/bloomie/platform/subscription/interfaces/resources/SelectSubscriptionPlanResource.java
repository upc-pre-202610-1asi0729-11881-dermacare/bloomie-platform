package com.bloomie.platform.subscription.interfaces.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "SelectSubscriptionPlanRequest",
        description = "Request payload for selecting subscription plan",
        example = "{\"PatientId\":1, \"PlanId\": 2}"
)
public record SelectSubscriptionPlanResource(

        @Schema(description = "Patient user id", example = "1")
        Long patientId,

        @Schema(description = "Plan id to subscribe to", example = "1")
        Long plandId
){
}
