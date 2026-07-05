package com.bloomie.platform.payments.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Resource for a payment.
 */
@Schema(
        name = "PaymentResponse",
        description = "Payment information response",
        example = "{\"id\": 1, \"patientId\":1, \"planId\": 2, \"subscriptionId\": 1, \"appointmentId\": null, \"dermatologistId\": null, \"paymentType\": \"SUBSCRIPTION\", \"paymentAmount\": 9.99, \"platformFeeAmount\": null, \"paymentStatus\": \"PENDING\"}"
)
public record PaymentResource(
        @Schema(description = "Payment unique identifier", example = "1")
        Long id,

        @Schema(description = "Patient unique identifier", example = "1")
        Long patientId,

        @Schema(description = "Plan unique identifier; only set for SUBSCRIPTION and RENEWAL payments", example = "1")
        Long planId,

        @Schema(description = "Subscription unique identifier; only set for SUBSCRIPTION and RENEWAL payments", example = "1")
        Long subscriptionId,

        @Schema(description = "Appointment unique identifier; only set for CONSULTATION payments", example = "1")
        Long appointmentId,

        @Schema(description = "Dermatologist IAM user id; only set for CONSULTATION payments", example = "1")
        Long dermatologistId,

        @Schema(description = "Payment type", example = "SUBSCRIPTION")
        String paymentType,

        @Schema(description = "Payment amount", example = "9.99")
        Double paymentAmount,

        @Schema(description = "Platform monetization cut of paymentAmount; only set for CONSULTATION payments", example = "7.5")
        Double platformFeeAmount,

        @Schema(description = "Payment status", example = "PENDING")
        String paymentStatus
) {
}
