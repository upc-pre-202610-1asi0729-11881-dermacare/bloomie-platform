// SubscriptionPlanSelectedEvent.java
package com.bloomie.platform.subscription.domain.model.events;

import com.bloomie.platform.subscription.domain.model.aggregates.Subscription;

public record SubscriptionPlanSelectedEvent(
        Long subscriptionId,
        Long patientId,
        Long planId) {

    public static SubscriptionPlanSelectedEvent from(Subscription subscription) {
        return new SubscriptionPlanSelectedEvent(
                subscription.getId(),
                subscription.getPatientId(),
                subscription.getPlanId());
    }
}