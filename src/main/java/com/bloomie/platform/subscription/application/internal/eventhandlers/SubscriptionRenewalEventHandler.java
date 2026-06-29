package com.bloomie.platform.subscription.application.internal.eventhandlers;

import com.bloomie.platform.payments.interfaces.events.SubscriptionRenewalPaymentProcessedIntegrationEvent;
import com.bloomie.platform.subscription.application.commandservices.SubscriptionCommandService;
import com.bloomie.platform.subscription.domain.model.commands.RenewSubscriptionCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Policy that renews a subscription once the Payments BC confirms the renewal payment.
 *
 * <p>Listens for {@link SubscriptionRenewalPaymentProcessedIntegrationEvent} published by the
 * Payments BC and dispatches a {@link RenewSubscriptionCommand} to extend the
 * subscription's billing period.</p>
 */
@Service
@Slf4j
public class SubscriptionRenewalEventHandler {

    private final SubscriptionCommandService subscriptionCommandService;

    public SubscriptionRenewalEventHandler(SubscriptionCommandService subscriptionCommandService) {
        this.subscriptionCommandService = subscriptionCommandService;
    }

    @EventListener
    public void on(SubscriptionRenewalPaymentProcessedIntegrationEvent event) {
        log.info("Renewal payment confirmed for subscription {}. Extending billing period.", event.subscriptionId());

        var command = new RenewSubscriptionCommand(event.subscriptionId());
        var result = subscriptionCommandService.handle(command);

        if (result.isFailure()) {
            log.warn("Failed to renew subscription {}: {}", event.subscriptionId(), result);
        }
    }
}
