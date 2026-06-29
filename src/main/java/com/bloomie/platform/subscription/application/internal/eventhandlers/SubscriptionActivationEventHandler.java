package com.bloomie.platform.subscription.application.internal.eventhandlers;

import com.bloomie.platform.payments.interfaces.events.SubscriptionPaymentProcessedIntegrationEvent;
import com.bloomie.platform.subscription.application.commandservices.SubscriptionCommandService;
import com.bloomie.platform.subscription.domain.model.commands.ActivateSubscriptionCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Policy that activates a subscription once the Payments BC confirms the initial payment.
 *
 * <p>Listens for {@link SubscriptionPaymentProcessedIntegrationEvent} published by the Payments BC
 * and dispatches an {@link ActivateSubscriptionCommand} to transition the subscription
 * from PENDING to ACTIVE.</p>
 */
@Service
@Slf4j
public class SubscriptionActivationEventHandler {

    private final SubscriptionCommandService subscriptionCommandService;

    public SubscriptionActivationEventHandler(SubscriptionCommandService subscriptionCommandService) {
        this.subscriptionCommandService = subscriptionCommandService;
    }

    @EventListener
    public void on(SubscriptionPaymentProcessedIntegrationEvent event) {
        log.info("Payment confirmed for patient {}. Activating subscription.", event.patientId());

        var command = new ActivateSubscriptionCommand(event.patientId(), event.planId());
        var result = subscriptionCommandService.handle(command);

        if (result.isFailure()) {
            log.warn("Failed to activate subscription for patient {}: {}",
                    event.patientId(), result);
        }
    }
}
