package com.bloomie.platform.skinanalysis.application.internal.eventhandlers;

import com.bloomie.platform.skinanalysis.domain.model.events.SkinProfileCompletedEvent;
import com.bloomie.platform.skinanalysis.interfaces.events.SkinProfileCompletedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Reacts to the internal {@link SkinProfileCompletedEvent} and re-publishes it as a
 * {@link SkinProfileCompletedIntegrationEvent} for consumption by other bounded contexts.
 */
@Service
public class SkinProfileCompletedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public SkinProfileCompletedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(SkinProfileCompletedEvent event) {
        eventPublisher.publishEvent(new SkinProfileCompletedIntegrationEvent(
                event.skinProfileId(),
                event.patientId(),
                event.skinType()));
    }
}
