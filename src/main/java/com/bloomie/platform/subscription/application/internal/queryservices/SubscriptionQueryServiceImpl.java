package com.bloomie.platform.subscription.application.internal.queryservices;

import com.bloomie.platform.subscription.application.queryservices.SubscriptionQueryService;
import com.bloomie.platform.subscription.domain.model.aggregates.Subscription;
import com.bloomie.platform.subscription.domain.model.entities.Plan;
import com.bloomie.platform.subscription.domain.model.queries.GetAllPlansQuery;
import com.bloomie.platform.subscription.domain.model.queries.GetPlanByIdQuery;
import com.bloomie.platform.subscription.domain.model.queries.GetSubscriptionByIdQuery;
import com.bloomie.platform.subscription.domain.model.queries.GetSubscriptionByPatientIdQuery;
import com.bloomie.platform.subscription.domain.model.valueobjects.PlanId;
import com.bloomie.platform.subscription.domain.repositories.SubscriptionRepository;
import org.hibernate.annotations.DialectOverride;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionQueryServiceImpl implements SubscriptionQueryService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionQueryServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public Optional<Plan> handle(GetPlanByIdQuery query) {
        return subscriptionRepository.findPlanById(new PlanId(query.planId()));
    }

    @Override
    public List<Plan> handle(GetAllPlansQuery query) {
        return subscriptionRepository.findAllPlans();
    }

    @Override
    public Optional<Subscription> handle(GetSubscriptionByIdQuery query) {
        return subscriptionRepository.findById(query.subscriptionId());
    }

    @Override
    public Optional<Subscription> handle(GetSubscriptionByPatientIdQuery query) {
        return subscriptionRepository.findByPatientId(query.patientId());
    }
}
