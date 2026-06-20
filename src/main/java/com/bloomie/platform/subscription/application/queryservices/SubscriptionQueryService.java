package com.bloomie.platform.subscription.application.queryservices;

import com.bloomie.platform.subscription.domain.model.aggregates.Subscription;
import com.bloomie.platform.subscription.domain.model.entities.Plan;
import com.bloomie.platform.subscription.domain.model.queries.GetAllPlansQuery;
import com.bloomie.platform.subscription.domain.model.queries.GetPlanByIdQuery;
import com.bloomie.platform.subscription.domain.model.queries.GetSubscriptionByIdQuery;
import com.bloomie.platform.subscription.domain.model.queries.GetSubscriptionByPatientIdQuery;

import java.util.List;
import java.util.Optional;

public interface SubscriptionQueryService {
    Optional<Plan> handle(GetPlanByIdQuery query);
    List<Plan> handle(GetAllPlansQuery query);
    Optional<Subscription> handle(GetSubscriptionByIdQuery query);
    Optional<Subscription> handle(GetSubscriptionByPatientIdQuery query);
}
