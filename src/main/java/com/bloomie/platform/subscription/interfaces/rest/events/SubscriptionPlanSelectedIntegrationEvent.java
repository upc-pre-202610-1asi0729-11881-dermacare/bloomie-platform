package com.bloomie.platform.subscription.interfaces.rest.events;

import com.bloomie.platform.subscription.domain.model.aggregates.Subscription;

public record SubscriptionPlanSelectedIntegrationEvent(Long patientId, Long planId) {
    public static SubscriptionPlanSelectedIntegrationEvent from(Subscription subscription) {
        return new SubscriptionPlanSelectedIntegrationEvent(
                subscription.getPatientId(),
                subscription.getPlanId()
        );
    }
}
