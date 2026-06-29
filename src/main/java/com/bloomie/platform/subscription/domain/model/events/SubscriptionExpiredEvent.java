package com.bloomie.platform.subscription.domain.model.events;

import com.bloomie.platform.subscription.domain.model.aggregates.Subscription;

import java.time.LocalDateTime;

/**
 * Domain event raised when a subscription expires.
 *
 * <p>Consumed by other bounded contexts (e.g., Payments) that need to react
 * when a patient's subscription period ends without a successful renewal.</p>
 *
 * @param subscriptionId the expired subscription identifier
 * @param patientId      the patient who owned the subscription
 * @param planId         the plan that expired
 * @param expiredAt      the moment the subscription was marked as expired
 */
public record SubscriptionExpiredEvent(
        Long subscriptionId,
        Long patientId,
        Long planId,
        LocalDateTime expiredAt) {

    /**
     * Creates a {@link SubscriptionExpiredEvent} from the given {@link Subscription} aggregate.
     *
     * @param subscription the subscription that expired
     * @return the domain event
     */
    public static SubscriptionExpiredEvent from(Subscription subscription) {
        return new SubscriptionExpiredEvent(
                subscription.getId(),
                subscription.getPatientId(),
                subscription.getPlanId(),
                LocalDateTime.now());
    }
}
