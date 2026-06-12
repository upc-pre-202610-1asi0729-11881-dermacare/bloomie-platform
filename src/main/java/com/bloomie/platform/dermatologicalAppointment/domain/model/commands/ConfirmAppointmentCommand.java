package com.bloomie.platform.dermatologicalAppointment.domain.model.commands;

/**
 * Command to confirm a scheduled dermatological appointment.
 *
 * <p>Issued by the patient (Young Adult) to explicitly move the appointment from
 * {@code SCHEDULED} to {@code CONFIRMED} status. Only appointments currently in
 * {@code SCHEDULED} status can be confirmed.</p>
 *
 * @param appointmentId the id of the appointment to confirm
 * @param patientId     the IAM user id of the patient confirming the appointment;
 *                      must match the appointment owner
 */
public record ConfirmAppointmentCommand(Long appointmentId, Long patientId) {
}
