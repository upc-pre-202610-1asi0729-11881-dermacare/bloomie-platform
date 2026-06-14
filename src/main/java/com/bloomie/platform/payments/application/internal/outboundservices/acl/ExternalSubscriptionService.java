package com.bloomie.platform.payments.application.internal.outboundservices.acl;

import com.bloomie.platform.subscription.domain.model.entities.Plan;
import com.bloomie.platform.subscription.interfaces.acl.SubscriptionContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExternalSubscriptionService {
    private final SubscriptionContextFacade subscriptionContextFacade;

    public ExternalSubscriptionService(SubscriptionContextFacade subscriptionContextFacade) {
        this.subscriptionContextFacade = subscriptionContextFacade;
    }

    public Optional<Long> fetchPlanById(Long planId) {
        boolean exists = subscriptionContextFacade.existsPlanById(planId);
        return exists ? Optional.of(planId) : Optional.empty();
    }

    public Optional<Double> fetchPlanPrice(Long planId) {
        var price = subscriptionContextFacade.fetchPlanPrice(planId);
        return price == null? Optional.empty() : Optional.of(price);
    }
}
