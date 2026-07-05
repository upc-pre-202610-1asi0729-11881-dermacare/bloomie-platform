package com.bloomie.platform.subscription.application.internal.commandservices;

import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;
import com.bloomie.platform.subscription.application.commandservices.SubscriptionCommandService;
import com.bloomie.platform.subscription.application.internal.outboundservices.acl.ExternalIamService;
import com.bloomie.platform.subscription.domain.model.aggregates.Subscription;
import com.bloomie.platform.subscription.domain.model.commands.ActivateSubscriptionCommand;
import com.bloomie.platform.subscription.domain.model.commands.CancelSubscriptionCommand;
import com.bloomie.platform.subscription.domain.model.commands.ChangeSubscriptionPlanCommand;
import com.bloomie.platform.subscription.domain.model.commands.ExpireSubscriptionCommand;
import com.bloomie.platform.subscription.domain.model.commands.RenewSubscriptionCommand;
import com.bloomie.platform.subscription.domain.model.commands.SelectSubscriptionPlanCommand;
import com.bloomie.platform.subscription.domain.model.valueobjects.PatientId;
import com.bloomie.platform.subscription.domain.model.valueobjects.PlanId;
import com.bloomie.platform.subscription.domain.model.valueobjects.SubscriptionStatus;
import com.bloomie.platform.subscription.domain.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionCommandServiceImpl implements SubscriptionCommandService {

    private static final String PATIENT_NOT_FOUND = "subscription.patient.not.found";
    private static final String SUBSCRIPTION_NOT_FOUND = "subscription.not.found";
    private static final String SUBSCRIPTION_CANNOT_ACTIVATE = "subscription.cannot.activate";
    private static final String SUBSCRIPTION_CANNOT_CANCEL = "subscription.cannot.cancel";
    private static final String SUBSCRIPTION_CANNOT_RENEW = "subscription.cannot.renew";
    private static final String SUBSCRIPTION_CANNOT_EXPIRE = "subscription.cannot.expire";
    private static final String SUBSCRIPTION_CANNOT_CHANGE_PLAN = "subscription.cannot.change.plan";

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

        var planId = new PlanId(command.planId());
        var plan = subscriptionRepository.findPlanById(planId);
        if (plan.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Plan", command.planId().toString()));
        }

        // A patient can only have one subscription row. If they already have one that is
        // still ACTIVE/PENDING, selecting a plan again is a genuine conflict. But if their
        // previous subscription lapsed (CANCELLED/EXPIRED), reactivate that same row onto
        // the newly selected plan instead of rejecting the resubscription outright.
        var existing = subscriptionRepository.findByPatientId(patientId).orElse(null);
        if (existing != null) {
            if (existing.getStatus() == SubscriptionStatus.ACTIVE || existing.getStatus() == SubscriptionStatus.PENDING) {
                return Result.failure(ApplicationError.conflict("Plan", "A plan with patient id '%s' already exists".formatted(command.patientId())));
            }
            existing.resubscribe(planId);
            try {
                var saved = subscriptionRepository.save(existing);
                return Result.success(saved.getId());
            } catch (Exception e) {
                return Result.failure(ApplicationError.unexpected("select-subscription-plan", e.getMessage()));
            }
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
    public Result<Subscription, ApplicationError> handle(ActivateSubscriptionCommand command) {
        var patientId = new PatientId(command.patientId());
        var subscription = subscriptionRepository.findByPatientId(patientId).orElse(null);
        if (subscription == null) {
            return Result.failure(ApplicationError.notFound("Subscription", SUBSCRIPTION_NOT_FOUND));
        }

        // Only PENDING subscriptions can be activated; any other status is a logic error
        if (subscription.getStatus() != SubscriptionStatus.PENDING) {
            return Result.failure(ApplicationError.businessRuleViolation("activate-subscription", SUBSCRIPTION_CANNOT_ACTIVATE));
        }

        var plan = subscriptionRepository.findPlanById(subscription.getPlanIdValue()).orElse(null);
        if (plan == null) {
            return Result.failure(ApplicationError.notFound("Plan", subscription.getPlanId().toString()));
        }

        subscription.activate(plan.getDurationDays());
        try {
            var saved = subscriptionRepository.save(subscription);
            return Result.success(saved);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("activate-subscription", e.getMessage()));
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

    @Override
    public Result<Subscription, ApplicationError> handle(ChangeSubscriptionPlanCommand command) {
        if (command.subscriptionId() == null || command.newPlanId() == null) {
            return Result.failure(ApplicationError.validationError("change-subscription-plan", "subscription.plan.id.invalid"));
        }

        var subscription = subscriptionRepository.findById(command.subscriptionId()).orElse(null);
        if (subscription == null) {
            return Result.failure(ApplicationError.notFound("Subscription", SUBSCRIPTION_NOT_FOUND));
        }

        // A CANCELLED subscription still grants access until its paid endDate elapses
        // (cancelling only stops auto-renewal). Switching plans during that grace period
        // is treated as the patient changing their mind, so it un-cancels the subscription
        // instead of being blocked. Only a truly lapsed subscription (EXPIRED, or CANCELLED
        // past its endDate) cannot change plan — there is nothing left to switch.
        boolean lapsed = subscription.getStatus() == SubscriptionStatus.EXPIRED
                || (subscription.getStatus() == SubscriptionStatus.CANCELLED
                    && (subscription.getEndDate() == null || !subscription.getEndDate().isAfter(java.time.LocalDateTime.now())));
        if (lapsed) {
            return Result.failure(ApplicationError.businessRuleViolation("change-subscription-plan", SUBSCRIPTION_CANNOT_CHANGE_PLAN));
        }

        var newPlanId = new PlanId(command.newPlanId());
        if (newPlanId.equals(subscription.getPlanIdValue())) {
            return Result.failure(ApplicationError.conflict("Plan", "Subscription '%s' is already on plan '%s'".formatted(command.subscriptionId(), command.newPlanId())));
        }

        var plan = subscriptionRepository.findPlanById(newPlanId);
        if (plan.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Plan", command.newPlanId().toString()));
        }

        subscription.changePlan(newPlanId);
        try {
            var saved = subscriptionRepository.save(subscription);
            return Result.success(saved);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("change-subscription-plan", e.getMessage()));
        }
    }
}
