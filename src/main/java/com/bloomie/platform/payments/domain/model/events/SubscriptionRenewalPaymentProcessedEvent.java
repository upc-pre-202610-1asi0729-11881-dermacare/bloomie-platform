package com.bloomie.platform.payments.domain.model.events;

import com.bloomie.platform.payments.domain.model.aggregates.Payment;

/**
 * Domain event published when a subscription renewal payment is successfully processed.
 *
 * <p>The Subscription BC listens to the corresponding integration event to
 * extend the subscription's billing period via {@code RenewSubscriptionCommand}.</p>
 *
 * @param paymentId      the processed renewal payment identifier
 * @param patientId      the patient who was billed
 * @param planId         the plan that was renewed
 * @param subscriptionId the subscription linked to this renewal payment
 * @param amount         the monetary amount that was charged
 */
public record SubscriptionRenewalPaymentProcessedEvent(
        Long paymentId,
        Long patientId,
        Long planId,
        Long subscriptionId,
        Double amount) {

    /**
     * Convenience factory that extracts all needed fields from a saved {@link Payment}.
     *
     * @param payment the saved renewal payment (must already carry a non-null id)
     * @return a fully populated {@link SubscriptionRenewalPaymentProcessedEvent}
     */
    public static SubscriptionRenewalPaymentProcessedEvent from(Payment payment) {
        return new SubscriptionRenewalPaymentProcessedEvent(
                payment.getId(),
                payment.getPatientId(),
                payment.getPlanId(),
                payment.getSubscriptionId(),
                payment.getAmount());
    }
}
