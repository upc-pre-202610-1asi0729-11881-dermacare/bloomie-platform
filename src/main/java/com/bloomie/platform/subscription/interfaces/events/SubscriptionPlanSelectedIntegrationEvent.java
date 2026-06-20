package com.bloomie.platform.subscription.interfaces.events;

import com.bloomie.platform.subscription.domain.model.aggregates.Subscription;

public record SubscriptionPlanSelectedIntegrationEvent(Long subscriptionId, Long patientId, Long planId) {
    public static SubscriptionPlanSelectedIntegrationEvent from(Subscription subscription) {
        return new SubscriptionPlanSelectedIntegrationEvent(
                subscription.getId(),
                subscription.getPatientId(),
                subscription.getPlanId()
        );
    }
}
