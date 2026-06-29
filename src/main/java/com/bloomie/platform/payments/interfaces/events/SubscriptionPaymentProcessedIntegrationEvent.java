package com.bloomie.platform.payments.interfaces.events;

/**
 * Integration event published by the Payments BC after a subscription payment is processed.
 *
 * <p>Consumed by the Subscription BC to activate the subscription once the
 * initial payment has been confirmed.</p>
 *
 * @param paymentId      the processed payment identifier
 * @param patientId      the patient who made the payment
 * @param planId         the plan that was paid for
 * @param subscriptionId the subscription linked to this payment
 * @param amount         the monetary amount that was charged
 */
public record SubscriptionPaymentProcessedIntegrationEvent(
        Long paymentId,
        Long patientId,
        Long planId,
        Long subscriptionId,
        Double amount) {}
