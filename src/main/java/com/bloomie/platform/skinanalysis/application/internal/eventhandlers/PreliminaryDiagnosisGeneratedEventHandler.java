package com.bloomie.platform.skinanalysis.application.internal.eventhandlers;

import com.bloomie.platform.skinanalysis.domain.model.events.PreliminaryDiagnosisGeneratedEvent;
import com.bloomie.platform.skinanalysis.interfaces.events.PreliminaryDiagnosisGeneratedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Reacts to the internal {@link PreliminaryDiagnosisGeneratedEvent} and re-publishes it as a
 * {@link PreliminaryDiagnosisGeneratedIntegrationEvent} for consumption by other bounded contexts.
 */
@Service
public class PreliminaryDiagnosisGeneratedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public PreliminaryDiagnosisGeneratedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(PreliminaryDiagnosisGeneratedEvent event) {
        eventPublisher.publishEvent(new PreliminaryDiagnosisGeneratedIntegrationEvent(
                event.skinAnalysisId(),
                event.patientId(),
                event.overallScore()));
    }
}
