package com.bloomie.platform.dermatologicalappointment.domain.model.commands;

/**
 * Command to start the clinical consultation session for a confirmed appointment.
 *
 * <p>Issued by the dermatologist from the Consultation Session View once the patient is present.
 * Creates a new {@code Consultation} aggregate in {@code PENDING} status and immediately
 * transitions it to {@code IN_PROGRESS}. Only one consultation can exist per appointment.</p>
 *
 * @param appointmentId   the id of the confirmed appointment this consultation belongs to
 * @param dermatologistId the IAM user id of the dermatologist starting the session
 * @param patientId       the IAM user id of the patient; stored for reference in the consultation
 */
public record StartConsultationCommand(Long appointmentId, Long dermatologistId, Long patientId) {
}
