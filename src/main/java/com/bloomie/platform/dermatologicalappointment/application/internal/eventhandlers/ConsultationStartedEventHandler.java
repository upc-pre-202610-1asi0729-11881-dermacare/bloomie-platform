package com.bloomie.platform.dermatologicalAppointment.application.internal.eventhandlers;

import com.bloomie.platform.dermatologicalAppointment.application.commandservices.AppointmentCommandService;
import com.bloomie.platform.dermatologicalAppointment.domain.model.commands.MarkAppointmentInProgressCommand;
import com.bloomie.platform.dermatologicalAppointment.domain.model.events.ConsultationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Policy that transitions an appointment to {@code IN_PROGRESS} when its consultation starts.
 *
 * <p>Listens to {@link ConsultationStartedEvent} and dispatches a
 * {@link MarkAppointmentInProgressCommand} so the appointment status reflects the ongoing session.</p>
 */
@Service
public class ConsultationStartedEventHandler {

    private final AppointmentCommandService appointmentCommandService;

    public ConsultationStartedEventHandler(AppointmentCommandService appointmentCommandService) {
        this.appointmentCommandService = appointmentCommandService;
    }

    @EventListener
    public void on(ConsultationStartedEvent event) {
        appointmentCommandService.handle(new MarkAppointmentInProgressCommand(event.appointmentId()));
    }
}
