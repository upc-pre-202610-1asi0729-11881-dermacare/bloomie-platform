package com.bloomie.platform.payments.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "ProcessSubscriptionPaymentResponse",
        description = "Request payload for processing a subscription payment",
        example = "{\"patienId\":1, \"planId\": 2, \"subscriptionId\": 1 ,\"paymentAmount\": 9.99}"
)
public record ProcessSubscriptionPaymentResource(
        @Schema(description = "Patient unique identifier", example = "1")
        Long patientId,

        @Schema(description = "Plan unique identifier", example = "1")
        Long planId,

        @Schema(description = "Subscription unique identifier", example = "1")
        Long subscriptionId,

        @Schema(description = "Payment amount", example = "9.99")
        Double paymentAmount
) {
}
