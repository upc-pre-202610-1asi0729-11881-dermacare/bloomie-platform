package com.bloomie.platform.payments.application.internal.eventhandlers;

import com.bloomie.platform.payments.domain.model.events.SubscriptionRenewalPaymentProcessedEvent;
import com.bloomie.platform.payments.interfaces.events.SubscriptionRenewalPaymentProcessedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Policy that translates the internal {@link SubscriptionRenewalPaymentProcessedEvent} domain event
 * into a {@link SubscriptionRenewalPaymentProcessedIntegrationEvent} for other bounded contexts.
 *
 * <p>Follows the same outbound-translation pattern as {@link PaymentProcessedDomainEventHandler}
 * for initial subscription payments.</p>
 */
@Service
public class RenewalPaymentProcessedDomainEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public RenewalPaymentProcessedDomainEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(SubscriptionRenewalPaymentProcessedEvent event) {
        eventPublisher.publishEvent(new SubscriptionRenewalPaymentProcessedIntegrationEvent(
                event.paymentId(),
                event.patientId(),
                event.planId(),
                event.subscriptionId(),
                event.amount()));
    }
}
