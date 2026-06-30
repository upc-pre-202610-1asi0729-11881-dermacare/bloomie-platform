package com.bloomie.platform.payments.application.internal.outboundservices.stripe;

/**
 * Outbound port for Stripe Checkout Session creation.
 * The infrastructure layer provides the concrete Stripe implementation.
 */
public interface StripeCheckoutService {

    /**
     * Creates a Stripe Checkout Session for a subscription payment.
     *
     * @param patientId the IAM user id of the patient
     * @param planId    the subscription plan identifier
     * @param planName  the display name of the plan
     * @param amount    the payment amount in the plan's currency (USD)
     * @return the Stripe Checkout Session URL to redirect the user to
     */
    String createCheckoutSession(Long patientId, Long planId, String planName, Double amount);
}