package com.bloomie.platform.payments.interfaces.events;

/**
 * Integration event published by the Payments BC after a subscription renewal payment is processed.
 *
 * <p>Consumed by the Subscription BC to extend the subscription's billing period
 * via {@code RenewSubscriptionCommand}.</p>
 *
 * @param paymentId      the processed renewal payment identifier
 * @param patientId      the patient who was billed
 * @param planId         the plan that was renewed
 * @param subscriptionId the subscription linked to this renewal payment
 * @param amount         the monetary amount that was charged
 */
public record SubscriptionRenewalPaymentProcessedIntegrationEvent(
        Long paymentId,
        Long patientId,
        Long planId,
        Long subscriptionId,
        Double amount) {}
