package com.bloomie.platform.routinemanagement.application.internal.eventhandlers;

import com.bloomie.platform.routinemanagement.domain.model.events.PersonalizedRoutineGeneratedEvent;
import com.bloomie.platform.routinemanagement.interfaces.events.PersonalizedRoutineGeneratedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Reacts to the internal {@link PersonalizedRoutineGeneratedEvent} and re-publishes it as a
 * {@link PersonalizedRoutineGeneratedIntegrationEvent} for consumption by other bounded contexts.
 */
@Service
public class PersonalizedRoutineGeneratedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public PersonalizedRoutineGeneratedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(PersonalizedRoutineGeneratedEvent event) {
        eventPublisher.publishEvent(new PersonalizedRoutineGeneratedIntegrationEvent(
                event.routineId(),
                event.patientId(),
                event.skinAnalysisId()));
    }
}