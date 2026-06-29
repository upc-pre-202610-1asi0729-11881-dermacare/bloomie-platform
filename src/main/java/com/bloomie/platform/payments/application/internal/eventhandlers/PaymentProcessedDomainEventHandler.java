package com.bloomie.platform.payments.application.internal.eventhandlers;

import com.bloomie.platform.payments.domain.model.events.SubscriptionPaymentProcessedEvent;
import com.bloomie.platform.payments.interfaces.events.SubscriptionPaymentProcessedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Policy that translates the internal {@link SubscriptionPaymentProcessedEvent} domain event
 * into a {@link SubscriptionPaymentProcessedIntegrationEvent} for other bounded contexts.
 *
 * <p>Mirrors the same outbound-translation pattern used in the Subscription BC
 * ({@code SubscriptionPlanSelectedEventHandler}). Keeps the Payments BC's domain model
 * decoupled from its consumers.</p>
 */
@Service
public class PaymentProcessedDomainEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public PaymentProcessedDomainEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(SubscriptionPaymentProcessedEvent event) {
        eventPublisher.publishEvent(new SubscriptionPaymentProcessedIntegrationEvent(
                event.paymentId(),
                event.patientId(),
                event.planId(),
                event.subscriptionId(),
                event.amount()));
    }
}
