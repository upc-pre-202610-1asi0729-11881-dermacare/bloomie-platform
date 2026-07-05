package com.bloomie.platform.dermatologicalappointment.application.internal.eventhandlers;

import com.bloomie.platform.dermatologicalappointment.application.internal.outboundservices.acl.ExternalDermatologyCareService;
import com.bloomie.platform.dermatologicalappointment.domain.model.events.DermatologyAppointmentScheduledEvent;
import com.bloomie.platform.dermatologicalappointment.interfaces.events.RequestConsultationPaymentIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Policy that requests a consultation payment when a new appointment is scheduled.
 *
 * <p>Fetches the dermatologist's consultation fee from the Dermatology Care context and
 * translates {@link DermatologyAppointmentScheduledEvent} into a
 * {@link RequestConsultationPaymentIntegrationEvent} for the Payments BC.</p>
 */
@Service
public class DermatologyAppointmentScheduledEventHandler {

    private final ApplicationEventPublisher eventPublisher;
    private final ExternalDermatologyCareService externalDermatologyCareService;

    public DermatologyAppointmentScheduledEventHandler(ApplicationEventPublisher eventPublisher,
                                                        ExternalDermatologyCareService externalDermatologyCareService) {
        this.eventPublisher = eventPublisher;
        this.externalDermatologyCareService = externalDermatologyCareService;
    }

    @EventListener
    public void on(DermatologyAppointmentScheduledEvent event) {
        var consultationFee = externalDermatologyCareService.getConsultationFee(event.dermatologistId());
        eventPublisher.publishEvent(new RequestConsultationPaymentIntegrationEvent(
                event.appointmentId(),
                event.patientId(),
                event.dermatologistId(),
                event.scheduledAt(),
                consultationFee));
    }
}
