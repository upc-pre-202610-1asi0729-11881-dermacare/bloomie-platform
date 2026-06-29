package com.bloomie.platform.subscription.application.internal.commandservices;

import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;
import com.bloomie.platform.subscription.application.commandservices.SubscriptionCommandService;
import com.bloomie.platform.subscription.application.internal.outboundservices.acl.ExternalIamService;
import com.bloomie.platform.subscription.domain.model.aggregates.Subscription;
import com.bloomie.platform.subscription.domain.model.commands.CancelSubscriptionCommand;
import com.bloomie.platform.subscription.domain.model.commands.ExpireSubscriptionCommand;
import com.bloomie.platform.subscription.domain.model.commands.RenewSubscriptionCommand;
import com.bloomie.platform.subscription.domain.model.commands.SelectSubscriptionPlanCommand;
import com.bloomie.platform.subscription.domain.model.valueobjects.PlanId;
import com.bloomie.platform.subscription.domain.model.valueobjects.SubscriptionStatus;
import com.bloomie.platform.subscription.domain.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionCommandServiceImpl implements SubscriptionCommandService {

    private static final String PATIENT_NOT_FOUND = "subscription.patient.not.found";
    private static final String SUBSCRIPTION_NOT_FOUND = "subscription.not.found";
    private static final String SUBSCRIPTION_CANNOT_CANCEL = "subscription.cannot.cancel";
    private static final String SUBSCRIPTION_CANNOT_RENEW = "subscription.cannot.renew";
    private static final String SUBSCRIPTION_CANNOT_EXPIRE = "subscription.cannot.expire";

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

    @Override
    public Result<Subscription, ApplicationError> handle(CancelSubscriptionCommand command) {
        var subscription = subscriptionRepository.findById(command.subscriptionId()).orElse(null);
        if (subscription == null) {
            return Result.failure(ApplicationError.notFound("Subscription", SUBSCRIPTION_NOT_FOUND));
        }

        if (subscription.getStatus() == SubscriptionStatus.CANCELLED
                || subscription.getStatus() == SubscriptionStatus.EXPIRED) {
            return Result.failure(ApplicationError.businessRuleViolation("cancel-subscription", SUBSCRIPTION_CANNOT_CANCEL));
        }

        subscription.cancel();
        try {
            var saved = subscriptionRepository.save(subscription);
            return Result.success(saved);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("cancel-subscription", e.getMessage()));
        }
    }

    @Override
    public Result<Subscription, ApplicationError> handle(RenewSubscriptionCommand command) {
        var subscription = subscriptionRepository.findById(command.subscriptionId()).orElse(null);
        if (subscription == null) {
            return Result.failure(ApplicationError.notFound("Subscription", SUBSCRIPTION_NOT_FOUND));
        }

        if (subscription.getStatus() != SubscriptionStatus.ACTIVE) {
            return Result.failure(ApplicationError.businessRuleViolation("renew-subscription", SUBSCRIPTION_CANNOT_RENEW));
        }

        var plan = subscriptionRepository.findPlanById(subscription.getPlanIdValue()).orElse(null);
        if (plan == null) {
            return Result.failure(ApplicationError.notFound("Plan", subscription.getPlanId().toString()));
        }

        subscription.renew(plan.getDurationDays());
        try {
            var saved = subscriptionRepository.save(subscription);
            return Result.success(saved);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("renew-subscription", e.getMessage()));
        }
    }

    @Override
    public Result<Subscription, ApplicationError> handle(ExpireSubscriptionCommand command) {
        var subscription = subscriptionRepository.findById(command.subscriptionId()).orElse(null);
        if (subscription == null) {
            return Result.failure(ApplicationError.notFound("Subscription", SUBSCRIPTION_NOT_FOUND));
        }

        if (subscription.getStatus() != SubscriptionStatus.ACTIVE) {
            return Result.failure(ApplicationError.businessRuleViolation("expire-subscription", SUBSCRIPTION_CANNOT_EXPIRE));
        }

        subscription.expire();
        try {
            var saved = subscriptionRepository.save(subscription);
            return Result.success(saved);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("expire-subscription", e.getMessage()));
        }
    }
}
