package com.bloomie.platform.payments.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Request payload for processing a subscription renewal payment.
 */
@Schema(
        name = "ProcessRenewalPaymentRequest",
        description = "Request payload for processing a subscription renewal payment",
        example = "{\"patientId\": 1, \"planId\": 2, \"subscriptionId\": 1, \"amount\": 29.99}"
)
public record ProcessRenewalPaymentResource(

        @Schema(description = "Patient user id", example = "1")
        Long patientId,

        @Schema(description = "Plan id being renewed", example = "2")
        Long planId,

        @Schema(description = "Subscription id to renew", example = "1")
        Long subscriptionId,

        @Schema(description = "Renewal payment amount", example = "29.99")
        Double amount) {}
