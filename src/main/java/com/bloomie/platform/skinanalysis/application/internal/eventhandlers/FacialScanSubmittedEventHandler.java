package com.bloomie.platform.skinanalysis.application.internal.eventhandlers;

import com.bloomie.platform.skinanalysis.domain.model.events.FacialScanSubmittedEvent;
import com.bloomie.platform.skinanalysis.interfaces.events.FacialScanSubmittedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Reacts to the internal {@link FacialScanSubmittedEvent} and re-publishes it as a
 * {@link FacialScanSubmittedIntegrationEvent} for consumption by other bounded contexts.
 */
@Service
public class FacialScanSubmittedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public FacialScanSubmittedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(FacialScanSubmittedEvent event) {
        eventPublisher.publishEvent(new FacialScanSubmittedIntegrationEvent(
                event.facialScanId(),
                event.patientId()));
    }
}
