package com.bloomie.platform.subscription.application.acl;

import com.bloomie.platform.subscription.application.commandservices.SubscriptionCommandService;
import com.bloomie.platform.subscription.application.queryservices.SubscriptionQueryService;
import com.bloomie.platform.subscription.domain.model.entities.Plan;
import com.bloomie.platform.subscription.domain.model.queries.GetPlanByIdQuery;
import com.bloomie.platform.subscription.domain.model.queries.GetSubscriptionByIdQuery;
import com.bloomie.platform.subscription.domain.model.queries.GetSubscriptionByPatientIdQuery;
import com.bloomie.platform.subscription.domain.model.valueobjects.PatientId;
import com.bloomie.platform.subscription.domain.model.valueobjects.PlanId;
import com.bloomie.platform.subscription.interfaces.acl.SubscriptionContextFacade;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionContextFacadeImpl implements SubscriptionContextFacade {
    private final SubscriptionCommandService subscriptionCommandService;
    private final SubscriptionQueryService subscriptionQueryService;

    public SubscriptionContextFacadeImpl(SubscriptionCommandService subscriptionCommandService, SubscriptionQueryService subscriptionQueryService) {
        this.subscriptionCommandService = subscriptionCommandService;
        this.subscriptionQueryService = subscriptionQueryService;
    }

    @Override
    public boolean existsPlanById(Long id) {
        var query = new GetPlanByIdQuery(id);
        return subscriptionQueryService.handle(query).isPresent();
    }

    @Override
    public Double fetchPlanPrice(Long id) {
        var query = new GetPlanByIdQuery(id);
        return subscriptionQueryService.handle(query).map(plan -> plan.getPrice()).orElse(null);
    }
}
