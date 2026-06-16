package com.bloomie.platform.dermatologicalappointment.application.internal.eventhandlers;

import com.bloomie.platform.dermatologicalappointment.application.queryservices.AppointmentQueryService;
import com.bloomie.platform.dermatologicalappointment.domain.model.events.AppointmentCancelledEvent;
import com.bloomie.platform.dermatologicalappointment.domain.model.queries.GetAppointmentByIdQuery;
import com.bloomie.platform.dermatologicalappointment.interfaces.events.ProcessRefundIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Policy that evaluates refund eligibility when an appointment is cancelled.
 *
 * <p>Fetches the appointment and calls {@code isEligibleForRefund()} (scheduled time more than
 * 24 hours from now). If eligible, publishes a {@link ProcessRefundIntegrationEvent} for the
 * Subscription BC.</p>
 */
@Service
public class AppointmentCancelledEventHandler {

    private final AppointmentQueryService appointmentQueryService;
    private final ApplicationEventPublisher eventPublisher;

    public AppointmentCancelledEventHandler(AppointmentQueryService appointmentQueryService,
                                            ApplicationEventPublisher eventPublisher) {
        this.appointmentQueryService = appointmentQueryService;
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(AppointmentCancelledEvent event) {
        appointmentQueryService.handle(new GetAppointmentByIdQuery(event.appointmentId()))
                .ifPresent(appointment -> {
                    if (appointment.isEligibleForRefund()) {
                        eventPublisher.publishEvent(new ProcessRefundIntegrationEvent(
                                event.appointmentId(), event.patientId()));
                    }
                });
    }
}
