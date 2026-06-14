package com.bloomie.platform.payments.domain.model.commands;

public record ProcessSubscriptionPaymentCommand(Long patientId, Long planId, Long subscriptionId, Double amount) {
}
