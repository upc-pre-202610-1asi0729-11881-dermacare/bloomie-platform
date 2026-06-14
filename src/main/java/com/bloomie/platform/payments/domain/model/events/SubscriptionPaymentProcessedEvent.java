package com.bloomie.platform.payments.domain.model.events;

import com.bloomie.platform.payments.domain.model.aggregates.Payment;

/**
 * Domain event published when a subscription payment is successfully processed and persisted.
 *
 * <p>Other bounded contexts can listen to this event to react to payment completion
 * without directly coupling to the {@code payments} application services.</p>
 *
 * @param paymentId      The identity assigned to the processed payment.
 * @param patientId      The patient who originated the payment.
 * @param planId         The subscription plan associated with the payment.
 * @param subscriptionId The subscription for which the payment was made.
 * @param amount         The monetary amount of the payment.
 */
public record SubscriptionPaymentProcessedEvent(Long paymentId, Long patientId, Long planId, Long subscriptionId, Double amount) {

    /**
     * Convenience factory that extracts all needed fields from a saved {@link Payment}.
     *
     * @param payment the saved payment (must already carry a non-null id)
     * @return a fully populated {@link SubscriptionPaymentProcessedEvent}
     */
    public static SubscriptionPaymentProcessedEvent from(Payment payment) {
        return new SubscriptionPaymentProcessedEvent(
                payment.getId(),
                payment.getPatientId(),
                payment.getPlanId(),
                payment.getSubscriptionId(),
                payment.getAmount()
        );
    }
}
