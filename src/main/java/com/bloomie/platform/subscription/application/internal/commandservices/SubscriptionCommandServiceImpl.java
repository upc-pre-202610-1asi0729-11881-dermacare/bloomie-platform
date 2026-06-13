package com.bloomie.platform.subscription.application.internal.commandservices;

import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;
import com.bloomie.platform.subscription.application.commandservices.SubscriptionCommandService;
import com.bloomie.platform.subscription.application.internal.outboundservices.acl.ExternalIamService;
import com.bloomie.platform.subscription.domain.model.aggregates.Subscription;
import com.bloomie.platform.subscription.domain.model.commands.SelectSubscriptionPlanCommand;
import com.bloomie.platform.subscription.domain.model.valueobjects.PlanId;
import com.bloomie.platform.subscription.domain.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionCommandServiceImpl implements SubscriptionCommandService {

    private static final String PATIENT_NOT_FOUND = "subscription.patient.not.found";

    private final SubscriptionRepository subscriptionRepository;
    private final ExternalIamService externalIamService;

    public SubscriptionCommandServiceImpl(SubscriptionRepository subscriptionRepository,
                                          ExternalIamService externalIamService) {
        this.subscriptionRepository = subscriptionRepository;
        this.externalIamService = externalIamService;
    }

    @Override
    public Result<Long, ApplicationError> handle(SelectSubscriptionPlanCommand command) {
        if (command.patientId() == null || command.planId() == null) {
            return Result.failure(ApplicationError.validationError("select-subscription-plan", "subscription.patient.id.invalid"));
        }

        var patientId = externalIamService.fetchPatientById(command.patientId())
                .orElse(null);
        if (patientId == null) {
            return Result.failure(ApplicationError.notFound("Patient", PATIENT_NOT_FOUND));
        }

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
