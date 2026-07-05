package com.bloomie.platform.payments.domain.model.commands;

/**
 * Command to process a renewal payment for an existing active subscription.
 *
 * <p>Semantically distinct from {@link ProcessSubscriptionPaymentCommand}:
 * that command activates a subscription, whereas this one extends an already-active one.</p>
 *
 * @param patientId      the patient being billed
 * @param planId         the plan being renewed
 * @param subscriptionId the subscription whose billing period is extended
 * @param amount         the monetary amount to charge
 */
public record ProcessRenewalPaymentCommand(
        Long patientId,
        Long planId,
        Long subscriptionId,
        Double amount) {}
