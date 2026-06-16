package com.bloomie.platform.dermatologicalappointment.domain.model.events;

import com.bloomie.platform.dermatologicalappointment.domain.model.aggregates.Consultation;

/**
 * Domain event raised when the dermatologist progressively saves clinical notes mid-session.
 *
 * @param consultationId the id of the active consultation
 * @param appointmentId  the id of the associated appointment
 * @param notes          the current notes text that was saved
 */
public record ClinicalNotesSavedEvent(
        Long consultationId,
        Long appointmentId,
        String notes) {

    public static ClinicalNotesSavedEvent from(Consultation consultation) {
        return new ClinicalNotesSavedEvent(
                consultation.getId(),
                consultation.getAppointmentId(),
                consultation.getNotes().value());
    }
}
