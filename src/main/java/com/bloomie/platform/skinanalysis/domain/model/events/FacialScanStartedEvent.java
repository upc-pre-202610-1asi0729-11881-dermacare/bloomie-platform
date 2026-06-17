package com.bloomie.platform.skinanalysis.domain.model.events;

import com.bloomie.platform.skinanalysis.domain.model.aggregates.FacialScan;

/**
 * Domain event raised when a facial scan session has been successfully started.
 *
 * <p>Consumed by {@code FacialScanStartedEventHandler}, which re-publishes it as a
 * {@link com.bloomie.platform.skinanalysis.interfaces.events.FacialScanStartedIntegrationEvent}.</p>
 *
 * @param facialScanId the id of the started facial scan
 * @param patientId    the IAM user id of the patient
 */
public record FacialScanStartedEvent(Long facialScanId, Long patientId) {

    /** Factory method to build the event from the saved aggregate. */
    public static FacialScanStartedEvent from(FacialScan facialScan) {
        return new FacialScanStartedEvent(
                facialScan.getId(),
                facialScan.getPatientId());
    }
}
