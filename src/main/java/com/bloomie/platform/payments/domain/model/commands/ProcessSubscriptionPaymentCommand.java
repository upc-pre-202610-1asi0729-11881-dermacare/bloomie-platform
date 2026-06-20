package com.bloomie.platform.payments.domain.model.commands;

/**
 * Process Subscription Payment Command
 */
public record ProcessSubscriptionPaymentCommand(Long patientId, Long planId, Long subscriptionId, Double amount) {
}
