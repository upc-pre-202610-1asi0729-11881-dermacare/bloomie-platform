package com.bloomie.platform.subscription.application.internal.commandservices;

import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;
import com.bloomie.platform.subscription.application.commandservices.SubscriptionCommandService;
import com.bloomie.platform.subscription.domain.model.aggregates.Subscription;
import com.bloomie.platform.subscription.domain.model.commands.SelectSubscriptionPlanCommand;
import com.bloomie.platform.subscription.domain.model.valueobjects.PatientId;
import com.bloomie.platform.subscription.domain.model.valueobjects.PlanId;
import com.bloomie.platform.subscription.domain.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionCommandServiceImpl implements SubscriptionCommandService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionCommandServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public Result<Long, ApplicationError> handle(SelectSubscriptionPlanCommand command) {
        var patientId = new PatientId(command.patientId());
        if (subscriptionRepository.existsByPatientId(patientId)) {
            return Result.failure(ApplicationError.conflict("Plan", "A plan with patient id '%s' already exists".formatted(command.patientId())));
        }
        var planId = new PlanId(command.planId());
        var plan = subscriptionRepository.findPlanById(planId);
        if (plan.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Plan", command.planId().toString()));
        }

        var subscription = new Subscription(command);
        try {
            var saved = subscriptionRepository.save(subscription);
            return Result.success(saved.getId());
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("select-subscription-plan", e.getMessage()));
        }
    }
}
