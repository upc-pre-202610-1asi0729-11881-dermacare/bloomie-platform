package com.bloomie.platform.subscription.domain.repositories;

import com.bloomie.platform.subscription.domain.model.aggregates.Subscription;
import com.bloomie.platform.subscription.domain.model.entities.Plan;
import com.bloomie.platform.subscription.domain.model.valueobjects.PatientId;
import com.bloomie.platform.subscription.domain.model.valueobjects.PlanId;
import com.bloomie.platform.subscription.domain.model.valueobjects.SubscriptionStatus;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository {
    Optional<Subscription> findById(Long id);

    Optional<Subscription> findByPatientId(PatientId patientId);

    Subscription save(Subscription subscription);

    boolean existsById(Long id);

    boolean existsByPatientId(PatientId patientId);

    Optional<Plan> findPlanById(PlanId planId);

    List<Plan> findAllPlans();
}
