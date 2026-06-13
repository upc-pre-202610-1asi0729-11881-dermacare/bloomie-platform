package com.bloomie.platform.payments.domain.model.events;

import com.bloomie.platform.payments.domain.model.aggregates.Payment;

public record SubscriptionPaymentProcessedEvent(Long paymentId, Long patientId, Long planId, Double amount) {
    public static SubscriptionPaymentProcessedEvent from(Payment payment) {
        return new SubscriptionPaymentProcessedEvent(
                payment.getId(),
                payment.getPatientId(),
                payment.getPlanId(),
                payment.getAmount()
        );
    }
}
