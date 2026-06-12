package com.bloomie.platform.dermatologicalAppointment.application.internal.eventhandlers;

import com.bloomie.platform.dermatologicalAppointment.domain.model.events.DermatologyAppointmentScheduledEvent;
import com.bloomie.platform.dermatologicalAppointment.interfaces.events.RequestConsultationPaymentIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Policy that requests a consultation payment when a new appointment is scheduled.
 *
 * <p>Translates {@link DermatologyAppointmentScheduledEvent} into a
 * {@link RequestConsultationPaymentIntegrationEvent} for the Subscription BC.</p>
 */
@Service
public class DermatologyAppointmentScheduledEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public DermatologyAppointmentScheduledEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(DermatologyAppointmentScheduledEvent event) {
        eventPublisher.publishEvent(new RequestConsultationPaymentIntegrationEvent(
                event.appointmentId(),
                event.patientId(),
                event.dermatologistId(),
                event.scheduledAt()));
    }
}
