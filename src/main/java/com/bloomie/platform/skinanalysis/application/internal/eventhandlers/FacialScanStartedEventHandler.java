package com.bloomie.platform.skinanalysis.application.internal.eventhandlers;

import com.bloomie.platform.skinanalysis.domain.model.events.FacialScanStartedEvent;
import com.bloomie.platform.skinanalysis.interfaces.events.FacialScanStartedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Reacts to the internal {@link FacialScanStartedEvent} and re-publishes it as a
 * {@link FacialScanStartedIntegrationEvent} for consumption by other bounded contexts.
 */
@Service
public class FacialScanStartedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public FacialScanStartedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(FacialScanStartedEvent event) {
        eventPublisher.publishEvent(new FacialScanStartedIntegrationEvent(
                event.facialScanId(),
                event.patientId()));
    }
}
