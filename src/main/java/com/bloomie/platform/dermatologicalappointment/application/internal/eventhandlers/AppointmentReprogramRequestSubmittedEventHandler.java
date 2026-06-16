package com.bloomie.platform.dermatologicalappointment.application.internal.eventhandlers;

import com.bloomie.platform.dermatologicalappointment.application.commandservices.AppointmentCommandService;
import com.bloomie.platform.dermatologicalappointment.domain.model.commands.ReprogramAppointmentCommand;
import com.bloomie.platform.dermatologicalappointment.domain.model.events.AppointmentReprogramRequestSubmittedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Policy that validates the reprogram window before executing an appointment rescheduling.
 *
 * <p>If the current appointment time is more than {@value #REPROGRAM_WINDOW_MINIMUM_HOURS} hours
 * away, dispatches a {@link ReprogramAppointmentCommand} with the proposed new date. Otherwise
 * the request is silently discarded.</p>
 */
@Service
public class AppointmentReprogramRequestSubmittedEventHandler {

    private static final long REPROGRAM_WINDOW_MINIMUM_HOURS = 24L;

    private final AppointmentCommandService appointmentCommandService;

    public AppointmentReprogramRequestSubmittedEventHandler(AppointmentCommandService appointmentCommandService) {
        this.appointmentCommandService = appointmentCommandService;
    }

    @EventListener
    public void on(AppointmentReprogramRequestSubmittedEvent event) {
        var currentScheduledAt = LocalDateTime.parse(event.currentScheduledAt());
        long hoursUntilAppointment = ChronoUnit.HOURS.between(LocalDateTime.now(), currentScheduledAt);
        if (hoursUntilAppointment > REPROGRAM_WINDOW_MINIMUM_HOURS) {
            appointmentCommandService.handle(
                    new ReprogramAppointmentCommand(event.appointmentId(), event.requestedDate()));
        }
    }
}
