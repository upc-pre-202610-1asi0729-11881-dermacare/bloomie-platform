package com.bloomie.platform.dermatologicalappointment.domain.model.commands;

/**
 * Command to cancel an existing dermatological appointment.
 *
 * <p>Issued by the patient from the Appointment Summary View.
 * Only appointments in {@code SCHEDULED} or {@code CONFIRMED} status can be cancelled.
 * A non-blank {@code cancellationReason} is required by the domain.</p>
 *
 * @param appointmentId      the id of the appointment to cancel
 * @param patientId          the IAM user id of the requesting patient; must match the owner
 * @param cancellationReason the reason for cancellation; must not be blank
 */
public record CancelAppointmentCommand(Long appointmentId, Long patientId, String cancellationReason) {
}
