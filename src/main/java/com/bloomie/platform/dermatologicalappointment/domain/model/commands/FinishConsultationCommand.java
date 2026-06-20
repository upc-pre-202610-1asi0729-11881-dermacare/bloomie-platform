package com.bloomie.platform.dermatologicalappointment.domain.model.commands;

/**
 * Command to close and finalise a dermatological consultation session.
 *
 * <p>Issued by the Dermatologist from the Consultation Session View once all diagnosis notes
 * and clinical photos have been recorded. Transitions the consultation to {@code FINISHED} status.
 * The consultation must not already be in {@code FINISHED} status.</p>
 *
 * @param consultationId  the id of the consultation to finish
 * @param dermatologistId the IAM user id of the dermatologist closing the session
 */
public record FinishConsultationCommand(Long consultationId, Long dermatologistId) {
}
