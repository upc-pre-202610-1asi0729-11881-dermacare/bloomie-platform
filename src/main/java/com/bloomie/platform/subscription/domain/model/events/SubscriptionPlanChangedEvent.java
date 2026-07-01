package com.bloomie.platform.subscription.domain.model.events;

import com.bloomie.platform.subscription.domain.model.aggregates.Subscription;

/**
 * Domain event raised when a subscription is switched to a different plan.
 *
 * @param subscriptionId the subscription identifier
 * @param patientId      the patient who owns the subscription
 * @param previousPlanId the plan the subscription was previously on
 * @param newPlanId      the plan the subscription switched to
 */
public record SubscriptionPlanChangedEvent(
        Long subscriptionId,
        Long patientId,
        Long previousPlanId,
        Long newPlanId) {

    /**
     * Creates a {@link SubscriptionPlanChangedEvent} from the given {@link Subscription} aggregate.
     *
     * <p>{@code previousPlanId} is passed explicitly because it is transient state that only
     * exists on the aggregate instance that performed the mutation, not on the one reconstructed
     * from the persisted entity.</p>
     *
     * @param subscription   the subscription whose plan was changed
     * @param previousPlanId the plan id the subscription was previously on
     * @return the domain event
     */
    public static SubscriptionPlanChangedEvent from(Subscription subscription, Long previousPlanId) {
        return new SubscriptionPlanChangedEvent(
                subscription.getId(),
                subscription.getPatientId(),
                previousPlanId,
                subscription.getPlanId());
    }
}
