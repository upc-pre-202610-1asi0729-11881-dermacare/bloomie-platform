package com.bloomie.platform.subscription.domain.model.events;

import com.bloomie.platform.subscription.domain.model.aggregates.Subscription;

public record SubscriptionActivatedEvent(Long subscriptionId,
                                         Long patientId,
                                         Long planId) {

    public static SubscriptionActivatedEvent from(Subscription subscription) {
        return new SubscriptionActivatedEvent(
                subscription.getId(),
                subscription.getPatientId(),
                subscription.getPlanId());
    }
}
