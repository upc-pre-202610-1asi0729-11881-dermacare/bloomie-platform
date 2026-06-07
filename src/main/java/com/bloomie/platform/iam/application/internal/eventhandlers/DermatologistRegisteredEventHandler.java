package com.bloomie.platform.iam.application.internal.eventhandlers;

import com.bloomie.platform.iam.domain.model.events.DermatologistRegisteredEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Application event handler that reacts to {@link DermatologistRegisteredEvent}.
 *
 * <p>Receives the Spring Application Event published after a dermatologist is persisted
 * and can re-publish it to other bounded contexts or trigger side-effects such as
 * sending a welcome notification. Add a {@code @EventListener}-annotated method here
 * to implement cross-context integration logic.</p>
 */
@Service("usersDermatologistRegisteredEventHandler")
public class DermatologistRegisteredEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public DermatologistRegisteredEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
}
