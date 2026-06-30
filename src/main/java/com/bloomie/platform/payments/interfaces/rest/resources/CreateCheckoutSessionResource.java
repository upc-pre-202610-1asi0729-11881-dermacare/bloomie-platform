package com.bloomie.platform.payments.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * Request body for creating a Stripe Checkout Session.
 *
 * @param patientId the IAM user id of the patient
 * @param planId    the subscription plan identifier
 * @param planName  the display name of the selected plan
 * @param amount    the payment amount in USD
 */
@Schema(description = "Request payload for creating a Stripe Checkout Session")
public record CreateCheckoutSessionResource(
        @NotNull @Schema(example = "1")          Long   patientId,
        @NotNull @Schema(example = "1")          Long   planId,
        @NotNull @Schema(example = "Pro Plan")   String planName,
        @NotNull @Schema(example = "9.99")       Double amount
) {}