package com.bloomie.platform.payments.domain.model.valueobjects;

public record SubscriptionId(Long subscriptionId) {
    public SubscriptionId {
        if (subscriptionId == null || subscriptionId < 1)
            throw new IllegalArgumentException("SubscriptionId cannot be null or less than 1");
    }
}