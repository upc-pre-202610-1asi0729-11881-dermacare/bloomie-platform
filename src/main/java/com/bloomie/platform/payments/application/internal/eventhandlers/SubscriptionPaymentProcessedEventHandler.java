package com.bloomie.platform.payments.application.internal.eventhandlers;

import com.bloomie.platform.payments.application.commanservices.PaymentCommandService;
import com.bloomie.platform.payments.application.internal.outboundservices.acl.ExternalSubscriptionService;
import com.bloomie.platform.payments.domain.model.commands.ProcessSubscriptionPaymentCommand;
import com.bloomie.platform.subscription.interfaces.events.SubscriptionPlanSelectedIntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

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
            log.warn("Failed to process subscription payment for patient {}",
                    event.patientId());
        }
    }
}
