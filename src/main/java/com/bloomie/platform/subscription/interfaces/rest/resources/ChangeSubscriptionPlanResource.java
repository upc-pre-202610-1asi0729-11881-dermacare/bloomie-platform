package com.bloomie.platform.subscription.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "ChangeSubscriptionPlanRequest",
        description = "Request payload for changing a subscription's plan",
        example = "{\"newPlanId\": 2}"
)
public record ChangeSubscriptionPlanResource(

        @Schema(description = "Plan id to switch the subscription to", example = "2")
        Long newPlanId
){
}
