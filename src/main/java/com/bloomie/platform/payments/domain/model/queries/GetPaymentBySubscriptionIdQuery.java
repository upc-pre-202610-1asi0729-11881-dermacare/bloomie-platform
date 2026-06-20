package com.bloomie.platform.payments.domain.model.queries;

import com.bloomie.platform.payments.domain.model.valueobjects.SubscriptionId;

/**
 * Get Payment By Subscription ID Query
 */
public record GetPaymentBySubscriptionIdQuery(SubscriptionId subscriptionId) {
}
