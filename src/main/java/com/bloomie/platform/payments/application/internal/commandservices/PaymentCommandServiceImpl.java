package com.bloomie.platform.payments.application.internal.commandservices;

import com.bloomie.platform.payments.application.commanservices.PaymentCommandService;
import com.bloomie.platform.payments.application.internal.outboundservices.acl.ExternalSubscriptionService;
import com.bloomie.platform.payments.domain.model.aggregates.Payment;
import com.bloomie.platform.payments.domain.model.commands.ProcessRenewalPaymentCommand;
import com.bloomie.platform.payments.domain.model.commands.ProcessSubscriptionPaymentCommand;
import com.bloomie.platform.payments.domain.repositories.PaymentRepository;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Payment Command Service Implementation
 */
@Service
public class PaymentCommandServiceImpl implements PaymentCommandService {
    private final PaymentRepository paymentRepository;
    private final ExternalSubscriptionService externalSubscriptionService;

    /**
     * Constructor
     *
     * @param paymentRepository           The {@link PaymentRepository} instance
     * @param externalSubscriptionService The {@link ExternalSubscriptionService} instance
     */
    public PaymentCommandServiceImpl(PaymentRepository paymentRepository, ExternalSubscriptionService externalSubscriptionService) {
        this.paymentRepository = paymentRepository;
        this.externalSubscriptionService = externalSubscriptionService;
    }

    // inherited javadoc
    @Override
    public Result<Payment, ApplicationError> handle(ProcessSubscriptionPaymentCommand command) {
        var plan = externalSubscriptionService.fetchPlanById(command.planId());
        if (plan.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Plan", command.planId().toString()));
        }
        var payment = new Payment(command);
        try {
            var saved = paymentRepository.save(payment);
            return Result.success(saved);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("process-subscription-payment", e.getMessage()));
        }
    }

    // inherited javadoc
    @Override
    public Result<Payment, ApplicationError> handle(ProcessRenewalPaymentCommand command) {
        // Verify the plan still exists before charging
        var plan = externalSubscriptionService.fetchPlanById(command.planId());
        if (plan.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Plan", command.planId().toString()));
        }
        var payment = new Payment(command);
        try {
            var saved = paymentRepository.save(payment);
            return Result.success(saved);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("process-renewal-payment", e.getMessage()));
        }
    }
}
