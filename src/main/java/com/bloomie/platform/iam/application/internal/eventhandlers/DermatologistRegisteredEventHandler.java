package com.bloomie.platform.iam.application.internal.eventhandlers;

import com.bloomie.platform.iam.domain.model.events.DermatologistRegisteredEvent;
import com.bloomie.platform.iam.interfaces.events.DermatologistRegisteredIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Application event handler that reacts to {@link DermatologistRegisteredEvent}.
 *
 * <p>Translates the internal domain event into a {@link DermatologistRegisteredIntegrationEvent}
 * and re-publishes it so other bounded contexts (e.g. Dermatology Care) can react
 * without coupling to IAM's internal domain model.</p>
 */
@Service("usersDermatologistRegisteredEventHandler")
public class DermatologistRegisteredEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public DermatologistRegisteredEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(DermatologistRegisteredEvent event) {
        eventPublisher.publishEvent(new DermatologistRegisteredIntegrationEvent(
                event.userId(),
                event.firstName(),
                event.lastName(),
                event.email()));
    }
}
