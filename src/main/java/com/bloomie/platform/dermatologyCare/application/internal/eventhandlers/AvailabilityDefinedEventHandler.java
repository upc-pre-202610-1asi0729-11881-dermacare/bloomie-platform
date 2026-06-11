package com.bloomie.platform.dermatologyCare.application.internal.eventhandlers;

import com.bloomie.platform.dermatologyCare.domain.model.events.AvailabilityDefinedEvent;
import com.bloomie.platform.dermatologyCare.interfaces.events.AvailabilityDefinedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Reacts to the internal {@link AvailabilityDefinedEvent} and re-publishes it as an
 * {@link AvailabilityDefinedIntegrationEvent} for consumption by other bounded contexts
 * (e.g. Dermatological Appointment).
 */
@Service("dermatologyCareAvailabilityDefinedEventHandler")
public class AvailabilityDefinedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public AvailabilityDefinedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(AvailabilityDefinedEvent event) {
        eventPublisher.publishEvent(new AvailabilityDefinedIntegrationEvent(
                event.availabilityId(),
                event.dermatologistId(),
                event.dayOfWeek(),
                event.startTime(),
                event.endTime()));
    }
}
