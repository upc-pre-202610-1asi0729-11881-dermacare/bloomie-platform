package com.bloomie.platform.skinanalysis.interfaces.events;

/**
 * Integration event published by the {@code skinAnalysis} bounded context when a patient's
 * facial scan has been successfully submitted with a photo URL.
 *
 * <p>This is the <em>published language</em> of the skin analysis context.
 * Other bounded contexts should listen to this event rather than to the internal
 * {@link com.bloomie.platform.skinanalysis.domain.model.events.FacialScanSubmittedEvent}.</p>
 *
 * @param facialScanId the persistence id of the submitted facial scan
 * @param patientId    the IAM user id of the patient
 */
public record FacialScanSubmittedIntegrationEvent(Long facialScanId, Long patientId) {
}
