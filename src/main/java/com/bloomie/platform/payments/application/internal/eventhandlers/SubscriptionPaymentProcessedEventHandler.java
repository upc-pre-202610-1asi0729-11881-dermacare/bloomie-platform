package com.bloomie.platform.payments.application.internal.eventhandlers;

import com.bloomie.platform.payments.application.commandservices.PaymentCommandService;
import com.bloomie.platform.payments.application.internal.outboundservices.acl.ExternalSubscriptionService;
import com.bloomie.platform.payments.domain.model.aggregates.Payment;
import com.bloomie.platform.payments.domain.model.commands.ProcessSubscriptionPaymentCommand;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;
import com.bloomie.platform.subscription.interfaces.events.SubscriptionPlanSelectedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Policy that processes a subscription payment when a subscription plan is selected.
 *
 * <p>Listens for {@link SubscriptionPlanSelectedIntegrationEvent} from the Subscription BC,
 * fetches the plan price, and triggers the payment processing flow.</p>
 */
@Service("subscriptionPaymentProcessedEventHandler")
@Slf4j
public class SubscriptionPaymentProcessedEventHandler {
    private final PaymentCommandService paymentCommandService;
    private final ExternalSubscriptionService externalSubscriptionService;

    public SubscriptionPaymentProcessedEventHandler(PaymentCommandService paymentCommandService, ExternalSubscriptionService externalSubscriptionService) {
        this.paymentCommandService = paymentCommandService;
        this.externalSubscriptionService = externalSubscriptionService;
    }

    @EventListener
    public void on(SubscriptionPlanSelectedIntegrationEvent event) {
        var price = externalSubscriptionService.fetchPlanPrice(event.planId());
        if (price.isEmpty()) {
            log.warn("Plan not found for id: {}", event.planId());
            return;
        }

        var command = new ProcessSubscriptionPaymentCommand(
                event.patientId(),
                event.planId(),
                event.subscriptionId(),
                price.get());

        var result = paymentCommandService.handle(command);

        if (result.isFailure()) {
            var failure = (Result.Failure<Payment, ApplicationError>) result;
            log.warn("Failed to process subscription payment: code={}, message={}, details={}",
                    failure.error().code(),
                    failure.error().message(),
                    failure.error().details());
        }
    }
}
