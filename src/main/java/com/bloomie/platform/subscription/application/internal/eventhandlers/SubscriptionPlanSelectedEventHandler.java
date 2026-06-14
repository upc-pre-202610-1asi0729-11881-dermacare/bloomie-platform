package com.bloomie.platform.subscription.application.internal.eventhandlers;

import com.bloomie.platform.subscription.domain.model.events.SubscriptionPlanSelectedEvent;
import com.bloomie.platform.subscription.interfaces.events.SubscriptionPlanSelectedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Policy that translates a domain event into an integration event when a subscription plan is selected.
 *
 * <p>Translates {@link SubscriptionPlanSelectedEvent} into a
 * {@link SubscriptionPlanSelectedIntegrationEvent} for the Payments BC.</p>
 */
@Service("SubscriptionPlanSelectedEventHandler")
public class SubscriptionPlanSelectedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public SubscriptionPlanSelectedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(SubscriptionPlanSelectedEvent event) {
        eventPublisher.publishEvent((new SubscriptionPlanSelectedIntegrationEvent(event.subscriptionId(), event.patientId(), event.planId())));
    }
}
