package com.bloomie.platform.dermatologicalappointment.domain.model.events;

import com.bloomie.platform.dermatologicalappointment.domain.model.aggregates.Consultation;

/**
 * Domain event raised when the dermatologist has started the clinical consultation session.
 *
 * <p>Consumed by {@code ConsultationStartedEventHandler}, which marks the associated
 * appointment as {@code IN_PROGRESS}.</p>
 *
 * @param consultationId  the id of the started consultation
 * @param appointmentId   the id of the associated appointment
 * @param patientId       the IAM user id of the patient
 * @param dermatologistId the IAM user id of the dermatologist
 * @param startedAt       ISO-8601 string of the session start timestamp
 */
public record ConsultationStartedEvent(
        Long consultationId,
        Long appointmentId,
        Long patientId,
        Long dermatologistId,
        String startedAt) {

    public static ConsultationStartedEvent from(Consultation consultation) {
        return new ConsultationStartedEvent(
                consultation.getId(),
                consultation.getAppointmentId(),
                consultation.getPatientId().patientId(),
                consultation.getDermatologistId().dermatologistId(),
                consultation.getStartedAt());
    }
}
