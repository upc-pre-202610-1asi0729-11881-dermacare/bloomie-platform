package com.bloomie.platform.subscription.application.internal.eventhandlers;

import com.bloomie.platform.subscription.domain.model.events.SubscriptionPlanSelectedEvent;
import com.bloomie.platform.subscription.interfaces.events.SubscriptionPlanSelectedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service("SubscriptionPlanSelectedEventHandler")
public class SubscriptionPlanSelectedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * Constructor.
     *
     * @param eventPublisher Spring application event publisher
     */
    public SubscriptionPlanSelectedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }


    @EventListener
    public void on(SubscriptionPlanSelectedEvent event) {
        eventPublisher.publishEvent((new SubscriptionPlanSelectedIntegrationEvent(event.patientId(), event.planId())));
    }
}