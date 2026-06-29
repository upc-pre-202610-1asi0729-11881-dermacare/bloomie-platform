package com.bloomie.platform.payments.domain.model.events;

import com.bloomie.platform.payments.domain.model.aggregates.Payment;

/**
 * Domain event published when a payment is successfully refunded.
 *
 * @param paymentId      the refunded payment identifier
 * @param patientId      the patient who received the refund
 * @param planId         the plan associated with the refunded payment
 * @param subscriptionId the subscription linked to the refunded payment
 * @param amount         the monetary amount that was refunded
 */
public record PaymentRefundedEvent(
        Long paymentId,
        Long patientId,
        Long planId,
        Long subscriptionId,
        Double amount) {

    /**
     * Convenience factory that extracts all needed fields from the refunded {@link Payment}.
     *
     * @param payment the payment that was refunded
     * @return a fully populated {@link PaymentRefundedEvent}
     */
    public static PaymentRefundedEvent from(Payment payment) {
        return new PaymentRefundedEvent(
                payment.getId(),
                payment.getPatientId(),
                payment.getPlanId(),
                payment.getSubscriptionId(),
                payment.getAmount());
    }
}
