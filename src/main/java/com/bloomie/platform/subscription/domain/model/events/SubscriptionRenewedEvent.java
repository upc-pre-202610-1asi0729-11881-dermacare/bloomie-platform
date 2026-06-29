package com.bloomie.platform.subscription.domain.model.events;

import com.bloomie.platform.subscription.domain.model.aggregates.Subscription;

import java.time.LocalDateTime;

/**
 * Domain event raised when a subscription is successfully renewed.
 *
 * @param subscriptionId the renewed subscription identifier
 * @param patientId      the patient who owns the subscription
 * @param planId         the plan being renewed
 * @param newEndDate     the new expiry date after renewal
 */
public record SubscriptionRenewedEvent(
        Long subscriptionId,
        Long patientId,
        Long planId,
        LocalDateTime newEndDate) {

    /**
     * Creates a {@link SubscriptionRenewedEvent} from the given {@link Subscription} aggregate.
     *
     * @param subscription the subscription that was renewed
     * @return the domain event
     */
    public static SubscriptionRenewedEvent from(Subscription subscription) {
        return new SubscriptionRenewedEvent(
                subscription.getId(),
                subscription.getPatientId(),
                subscription.getPlanId(),
                subscription.getEndDate());
    }
}
