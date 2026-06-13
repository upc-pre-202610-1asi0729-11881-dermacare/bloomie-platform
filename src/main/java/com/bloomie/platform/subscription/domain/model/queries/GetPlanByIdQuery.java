// GetPlanByIdQuery.java
package com.bloomie.platform.subscription.domain.model.queries;

public record GetPlanByIdQuery(Long planId) {
    public GetPlanByIdQuery {
        if (planId == null || planId < 1)
            throw new IllegalArgumentException("PlanId cannot be null or less than 1");
    }
}