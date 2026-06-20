package com.bloomie.platform.skinanalysis.domain.model.commands;

/**
 * Command to submit a facial scan by attaching a photo URL.
 *
 * <p>On successful handling the {@code FacialScan} aggregate transitions from
 * {@code STARTED} to {@code SUBMITTED} and a {@code FacialScanSubmittedEvent} is raised.</p>
 *
 * @param facialScanId the id of the facial scan to submit
 * @param photoUrl     the URL of the uploaded photo
 */
public record SubmitFacialScanCommand(Long facialScanId, String photoUrl) {
}
