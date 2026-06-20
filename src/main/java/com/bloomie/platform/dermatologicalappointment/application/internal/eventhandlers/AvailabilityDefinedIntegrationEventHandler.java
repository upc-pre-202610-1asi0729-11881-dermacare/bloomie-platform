package com.bloomie.platform.dermatologicalappointment.application.internal.eventhandlers;

import com.bloomie.platform.dermatologycare.interfaces.events.AvailabilityDefinedIntegrationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Policy that reacts to a dermatologist's availability being defined in the Dermatology Care
 * bounded context.
 *
 * <p>Listens to {@link AvailabilityDefinedIntegrationEvent} published by the Dermatology Care
 * context and creates internal availability records for this bounded context so that patients
 * can discover and book available time slots.</p>
 *
 * <p><b>Note:</b> the full availability slot domain model is not yet implemented.
 * This handler exists to establish the integration point; internal slot management
 * logic should be added here once the corresponding aggregate is defined.</p>
 */
@Service
public class AvailabilityDefinedIntegrationEventHandler {

    /**
     * Handles an {@link AvailabilityDefinedIntegrationEvent} from the Dermatology Care context.
     *
     * @param event the integration event carrying the dermatologist's availability data
     */
    @EventListener
    public void on(AvailabilityDefinedIntegrationEvent event) {
        // Availability slot management will be implemented here once the internal
        // domain model for available time slots is defined.
    }
}
