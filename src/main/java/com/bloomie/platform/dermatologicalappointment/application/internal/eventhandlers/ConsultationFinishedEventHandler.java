package com.bloomie.platform.dermatologicalappointment.application.internal.eventhandlers;

import com.bloomie.platform.dermatologicalappointment.application.commandservices.AppointmentCommandService;
import com.bloomie.platform.dermatologicalappointment.domain.model.commands.CompleteAppointmentCommand;
import com.bloomie.platform.dermatologicalappointment.domain.model.events.ConsultationFinishedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Policy that completes an appointment when its associated consultation is finished.
 *
 * <p>Listens to {@link ConsultationFinishedEvent} and dispatches a
 * {@link CompleteAppointmentCommand} to transition the appointment from
 * {@code CONFIRMED} to {@code COMPLETED} status, closing the full lifecycle of a
 * scheduled dermatological visit.</p>
 */
@Service
public class ConsultationFinishedEventHandler {

    private final AppointmentCommandService appointmentCommandService;

    public ConsultationFinishedEventHandler(AppointmentCommandService appointmentCommandService) {
        this.appointmentCommandService = appointmentCommandService;
    }

    /**
     * Handles a {@link ConsultationFinishedEvent} by marking the associated appointment
     * as completed.
     *
     * @param event the domain event raised when the dermatologist finishes the consultation
     */
    @EventListener
    public void on(ConsultationFinishedEvent event) {
        appointmentCommandService.handle(new CompleteAppointmentCommand(event.appointmentId()));
    }
}
