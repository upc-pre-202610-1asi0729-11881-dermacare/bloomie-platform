package com.bloomie.platform.payments.domain.model.valueobjects;

/**
 * Value object representing a reference to a subscription from the Subscription bounded context.
 *
 * <p>Uses a {@code Long} internally, as required by the project convention when referencing
 * identities from other bounded contexts. The id must be a positive number to be valid.</p>
 *
 * @param subscriptionId the subscription identifier; must be a positive number
 */
public record SubscriptionId(Long subscriptionId) {

    private static final String INVALID_MESSAGE_KEY = "payment.subscription.id.invalid";

    /**
     * Compact constructor that validates the subscription id.
     *
     * @throws IllegalArgumentException if the subscriptionId is {@code null} or less than {@code 1}
     */
    public SubscriptionId {
        if (subscriptionId == null || subscriptionId < 1)
            throw new IllegalArgumentException(INVALID_MESSAGE_KEY);
    }
}
