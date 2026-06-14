package com.bloomie.platform.payments.domain.model.valueobjects;

/**
 * Value object representing a reference to a subscription plan from the Subscription bounded context.
 *
 * <p>Uses a {@code Long} internally, as required by the project convention when referencing
 * identities from other bounded contexts. The id must be a positive number to be valid.</p>
 *
 * @param planId the plan identifier; must be a positive number
 */
public record PlanId(Long planId) {

    private static final String INVALID_MESSAGE_KEY = "payment.plan.id.invalid";

    /**
     * Compact constructor that validates the plan id.
     *
     * @throws IllegalArgumentException if the planId is {@code null} or less than {@code 1}
     */
    public PlanId {
        if (planId == null || planId < 1)
            throw new IllegalArgumentException(INVALID_MESSAGE_KEY);
    }
}
