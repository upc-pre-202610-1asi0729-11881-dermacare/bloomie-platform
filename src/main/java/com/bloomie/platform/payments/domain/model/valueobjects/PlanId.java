package com.bloomie.platform.payments.domain.model.valueobjects;

public record PlanId(Long planId) {
    public PlanId {
        if (planId == null || planId < 1)
            throw new IllegalArgumentException("PlanId cannot be null or less than 1");
    }
}