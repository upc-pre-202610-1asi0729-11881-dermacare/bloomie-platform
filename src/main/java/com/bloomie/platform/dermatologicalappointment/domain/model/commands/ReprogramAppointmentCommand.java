package com.bloomie.platform.dermatologicalappointment.domain.model.commands;

/**
 * Command to execute the rescheduling of a dermatological appointment to a new date.
 *
 * <p>Dispatched internally by the {@code AppointmentReprogramRequestSubmittedEventHandler} policy
 * after it has validated that the reprogram window is still open (more than 24 h before the
 * current appointment time).</p>
 *
 * @param appointmentId the id of the appointment to reprogram
 * @param newDate       ISO-8601 local date-time string for the new confirmed date
 */
public record ReprogramAppointmentCommand(Long appointmentId, String newDate) {
}
