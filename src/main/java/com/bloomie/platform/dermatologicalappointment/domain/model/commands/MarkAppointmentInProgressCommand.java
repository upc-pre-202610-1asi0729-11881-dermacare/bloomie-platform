package com.bloomie.platform.dermatologicalappointment.domain.model.commands;

/**
 * Internal command to transition an appointment's status from {@code CONFIRMED} to
 * {@code IN_PROGRESS}.
 *
 * <p>Dispatched internally by the {@code ConsultationStartedEventHandler} policy when the
 * associated consultation is started by the dermatologist.</p>
 *
 * @param appointmentId the id of the appointment to mark as in progress
 */
public record MarkAppointmentInProgressCommand(Long appointmentId) {
}
