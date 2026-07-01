package com.bloomie.platform.subscription.infrastructure.adapters;

import com.bloomie.platform.subscription.domain.model.aggregates.Subscription;
import com.bloomie.platform.subscription.domain.model.entities.Plan;
import com.bloomie.platform.subscription.domain.model.valueobjects.PatientId;
import com.bloomie.platform.subscription.domain.model.valueobjects.PlanId;
import com.bloomie.platform.subscription.domain.model.valueobjects.SubscriptionStatus;
import com.bloomie.platform.subscription.domain.repositories.SubscriptionRepository;
import com.bloomie.platform.subscription.infrastructure.assemblers.PlanPersistenceAssembler;
import com.bloomie.platform.subscription.infrastructure.assemblers.SubscriptionPersistenceAssembler;
import com.bloomie.platform.subscription.infrastructure.repositories.PlanPersistenceRepository;
import com.bloomie.platform.subscription.infrastructure.repositories.SubscriptionPersistenceRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

    private final SubscriptionPersistenceRepository subscriptionPersistenceRepository;
    private final PlanPersistenceRepository planPersistenceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public SubscriptionRepositoryImpl(SubscriptionPersistenceRepository subscriptionPersistenceRepository,
                                      PlanPersistenceRepository planPersistenceRepository,
                                      ApplicationEventPublisher eventPublisher) {
        this.subscriptionPersistenceRepository = subscriptionPersistenceRepository;
        this.planPersistenceRepository = planPersistenceRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<Subscription> findById(Long id) {
        return subscriptionPersistenceRepository.findById(id)
                .map(SubscriptionPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<Subscription> findByPatientId(PatientId patientId) {
        return subscriptionPersistenceRepository.findByPatientId(patientId)
                .map(SubscriptionPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Subscription save(Subscription subscription) {
        boolean isNew = subscription.getId() == null;
        // Transient flags are read from the original aggregate BEFORE it is replaced
        // by the reconstructed savedSubscription, which always starts with all flags false.
        boolean isActivating = !isNew && subscription.isActivating();
        boolean isCancelling = !isNew && subscription.getStatus() == SubscriptionStatus.CANCELLED;
        boolean isExpiring  = !isNew && subscription.getStatus() == SubscriptionStatus.EXPIRED;
        boolean isRenewing  = !isNew && subscription.isRenewing();
        boolean isChangingPlan = !isNew && subscription.isChangingPlan();
        Long previousPlanId = isChangingPlan ? subscription.getPreviousPlanId() : null;
        var savedEntity = subscriptionPersistenceRepository.save(
                SubscriptionPersistenceAssembler.toPersistenceFromDomain(subscription));
        var savedSubscription = SubscriptionPersistenceAssembler.toDomainFromPersistence(savedEntity);
        if (isNew) {
            savedSubscription.onPlanSelected();
        } else if (isActivating) {
            savedSubscription.onActivated();
        } else if (isCancelling) {
            savedSubscription.onCancelled();
        } else if (isExpiring) {
            savedSubscription.onExpired();
        } else if (isRenewing) {
            savedSubscription.onRenewed();
        } else if (isChangingPlan) {
            savedSubscription.onPlanChanged(previousPlanId);
        }
        savedSubscription.domainEvents().forEach(eventPublisher::publishEvent);
        savedSubscription.clearDomainEvents();
        return savedSubscription;
    }

    @Override
    public boolean existsById(Long id) {
        return subscriptionPersistenceRepository.existsById(id);
    }

    @Override
    public boolean existsByPatientId(PatientId patientId) {
        return subscriptionPersistenceRepository.existsByPatientId(patientId);
    }

    @Override
    public Optional<Plan> findPlanById(PlanId planId) {
        return planPersistenceRepository.findById(planId.planId())
                .map(PlanPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Plan> findAllPlans() {
        return planPersistenceRepository.findAll().stream()
                .map(PlanPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }
}
