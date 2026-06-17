package com.bloomie.platform.skinanalysis.domain.model.events;

import com.bloomie.platform.skinanalysis.domain.model.aggregates.FacialScan;

/**
 * Domain event raised when a facial scan has been successfully submitted with a photo URL.
 *
 * <p>Consumed by {@code FacialScanSubmittedEventHandler}, which re-publishes it as a
 * {@link com.bloomie.platform.skinanalysis.interfaces.events.FacialScanSubmittedIntegrationEvent}.</p>
 *
 * @param facialScanId the id of the submitted facial scan
 * @param patientId    the IAM user id of the patient
 */
public record FacialScanSubmittedEvent(Long facialScanId, Long patientId) {

    /** Factory method to build the event from the saved aggregate. */
    public static FacialScanSubmittedEvent from(FacialScan facialScan) {
        return new FacialScanSubmittedEvent(
                facialScan.getId(),
                facialScan.getPatientId());
    }
}
