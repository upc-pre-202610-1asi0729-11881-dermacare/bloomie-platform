package com.bloomie.platform.subscription.domain.model.events;

import com.bloomie.platform.subscription.domain.model.aggregates.Subscription;

/**
 * Domain event raised when a subscription is cancelled.
 *
 * @param subscriptionId the cancelled subscription identifier
 * @param patientId      the patient who owned the subscription
 * @param planId         the plan that was cancelled
 */
public record SubscriptionCancelledEvent(Long subscriptionId, Long patientId, Long planId) {

    /**
     * Creates a {@link SubscriptionCancelledEvent} from the given {@link Subscription} aggregate.
     *
     * @param subscription the subscription that was cancelled
     * @return the domain event
     */
    public static SubscriptionCancelledEvent from(Subscription subscription) {
        return new SubscriptionCancelledEvent(
                subscription.getId(),
                subscription.getPatientId(),
                subscription.getPlanId());
    }
}
