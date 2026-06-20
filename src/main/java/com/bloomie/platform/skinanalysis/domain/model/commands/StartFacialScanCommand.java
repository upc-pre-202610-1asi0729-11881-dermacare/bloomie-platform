package com.bloomie.platform.skinanalysis.domain.model.commands;

/**
 * Command to start a new facial scan session for a patient.
 *
 * <p>On successful handling a {@code FacialScan} aggregate is created in status
 * {@code STARTED} and {@code FacialScanStartedEvent} is raised.</p>
 *
 * @param patientId the IAM user id of the patient starting the scan
 */
public record StartFacialScanCommand(Long patientId) {
}
