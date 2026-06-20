package com.bloomie.platform.dermatologicalappointment.domain.model.events;

import com.bloomie.platform.dermatologicalappointment.domain.model.aggregates.Consultation;

/**
 * Domain event raised when a clinical photo has been uploaded and added to a consultation.
 *
 * @param consultationId the id of the consultation
 * @param appointmentId  the id of the associated appointment
 * @param photoUrl       the URL of the photo that was just uploaded
 */
public record ClinicalPhotoUploadedEvent(
        Long consultationId,
        Long appointmentId,
        String photoUrl) {

    /** Creates the event carrying the last uploaded photo URL. */
    public static ClinicalPhotoUploadedEvent from(Consultation consultation, String uploadedUrl) {
        return new ClinicalPhotoUploadedEvent(
                consultation.getId(),
                consultation.getAppointmentId(),
                uploadedUrl);
    }
}
