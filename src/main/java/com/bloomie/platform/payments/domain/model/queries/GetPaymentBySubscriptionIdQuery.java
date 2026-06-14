package com.bloomie.platform.payments.domain.model.queries;

import com.bloomie.platform.payments.domain.model.valueobjects.SubscriptionId;

public record GetPaymentBySubscriptionIdQuery(SubscriptionId subscriptionId) {
}
