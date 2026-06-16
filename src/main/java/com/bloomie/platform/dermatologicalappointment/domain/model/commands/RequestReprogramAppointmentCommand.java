package com.bloomie.platform.dermatologicalAppointment.domain.model.commands;

/**
 * Command to request a date change for an existing dermatological appointment.
 *
 * <p>Issued by the patient from the Appointment Summary View. This command does not
 * directly reschedule the appointment; instead it triggers a policy that validates the
 * reprogram window before issuing the actual {@link ReprogramAppointmentCommand}.</p>
 *
 * @param appointmentId the id of the appointment to reschedule
 * @param patientId     the IAM user id of the requesting patient; must match the owner
 * @param newDate       ISO-8601 local date-time string for the proposed new date; must be future
 */
public record RequestReprogramAppointmentCommand(
        Long appointmentId,
        Long patientId,
        String newDate) {
}
