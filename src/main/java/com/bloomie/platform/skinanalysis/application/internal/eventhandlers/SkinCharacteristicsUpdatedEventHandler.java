package com.bloomie.platform.skinanalysis.application.internal.eventhandlers;

import com.bloomie.platform.skinanalysis.domain.model.events.SkinCharacteristicsUpdatedEvent;
import com.bloomie.platform.skinanalysis.interfaces.events.SkinCharacteristicsUpdatedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Reacts to the internal {@link SkinCharacteristicsUpdatedEvent} and re-publishes it as a
 * {@link SkinCharacteristicsUpdatedIntegrationEvent} for consumption by other bounded contexts.
 */
@Service
public class SkinCharacteristicsUpdatedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public SkinCharacteristicsUpdatedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(SkinCharacteristicsUpdatedEvent event) {
        eventPublisher.publishEvent(new SkinCharacteristicsUpdatedIntegrationEvent(
                event.skinProfileId(),
                event.patientId(),
                event.skinType()));
    }
}
