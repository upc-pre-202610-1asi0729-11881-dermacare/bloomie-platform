// GetSubscriptionByIdQuery.java
package com.bloomie.platform.subscription.domain.model.queries;

public record GetSubscriptionByIdQuery(Long subscriptionId) {
    public GetSubscriptionByIdQuery {
        if (subscriptionId == null || subscriptionId < 1)
            throw new IllegalArgumentException("SubscriptionId cannot be null or less than 1");
    }
}