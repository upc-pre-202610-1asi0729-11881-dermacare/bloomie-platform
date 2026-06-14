package com.bloomie.platform.subscription.interfaces.acl;

public interface SubscriptionContextFacade {
    boolean existsPlanById(Long id);
    Double fetchPlanPrice(Long planId);
}
