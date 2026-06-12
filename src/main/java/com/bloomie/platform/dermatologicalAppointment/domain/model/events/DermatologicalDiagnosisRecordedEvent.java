package com.bloomie.platform.dermatologicalAppointment.domain.model.events;

import com.bloomie.platform.dermatologicalAppointment.domain.model.aggregates.Consultation;

/**
 * Domain event raised when the dermatologist has recorded the final diagnosis and recommendations.
 *
 * @param consultationId  the id of the consultation
 * @param appointmentId   the id of the associated appointment
 * @param notes           the recorded clinical observations
 * @param recommendations the treatment recommendations
 */
public record DermatologicalDiagnosisRecordedEvent(
        Long consultationId,
        Long appointmentId,
        String notes,
        String recommendations) {

    public static DermatologicalDiagnosisRecordedEvent from(Consultation consultation) {
        return new DermatologicalDiagnosisRecordedEvent(
                consultation.getId(),
                consultation.getAppointmentId(),
                consultation.getNotes().value(),
                consultation.getRecommendations().value());
    }
}
