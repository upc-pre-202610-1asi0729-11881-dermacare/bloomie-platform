package com.bloomie.platform.payments.domain.model.commands;

/**
 * Command to refund a processed payment.
 *
 * <p>Only payments in {@code PROCESSED} status are eligible for refund.
 * Typically triggered when the patient cancels their subscription.</p>
 *
 * @param paymentId the identifier of the payment to refund
 */
public record RefundPaymentCommand(Long paymentId) {}
