package com.bloomie.platform.dermatologicalappointment.domain.model.commands;

/**
 * Command to attach a clinical photo to an ongoing consultation.
 *
 * <p>Issued by the Dermatologist during the clinical session. The photo URL must point to a
 * previously uploaded resource accessible via HTTP or HTTPS. The consultation must not
 * already be in {@code FINISHED} status.</p>
 *
 * @param consultationId the id of the consultation to which the photo is attached
 * @param photoUrl       the publicly accessible URL of the clinical photo; must be a valid http/https URL
 */
public record UploadClinicalPhotoCommand(Long consultationId, String photoUrl) {
}
