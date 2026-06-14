package com.bloomie.platform.payments.domain.model.valueobjects;

/**
 * Value object representing the monetary amount of a payment.
 *
 * <p>The amount must be zero or greater; negative values are not allowed.</p>
 *
 * @param amount the payment amount; must not be negative
 */
public record PaymentAmount(Double amount) {

    private static final String INVALID_MESSAGE_KEY = "payment.amount.negative";

    /**
     * Compact constructor that validates the payment amount.
     *
     * @throws IllegalArgumentException if the amount is negative
     */
    public PaymentAmount {
        if (amount < 0) {
            throw new IllegalArgumentException(INVALID_MESSAGE_KEY);
        }
    }
}
