package com.bloomie.platform.payments.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response with the Stripe Checkout Session URL.
 *
 * @param checkoutUrl the Stripe Checkout Session URL to redirect the user to
 */
@Schema(description = "Stripe Checkout Session response")
public record CheckoutSessionResource(
        @Schema(example = "https://checkout.stripe.com/pay/cs_test_...") String checkoutUrl
) {}