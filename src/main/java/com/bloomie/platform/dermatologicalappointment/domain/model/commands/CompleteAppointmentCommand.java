package com.bloomie.platform.dermatologicalAppointment.domain.model.commands;

/**
 * Command to mark a dermatological appointment as completed.
 *
 * <p>This command is not issued directly by any external actor. It is dispatched
 * internally by the {@code ConsultationFinishedEventHandler} policy when the associated
 * consultation session has been closed by the dermatologist.</p>
 *
 * @param appointmentId the id of the appointment to mark as completed
 */
public record CompleteAppointmentCommand(Long appointmentId) {
}
