package com.bloomie.platform.skinanalysis.interfaces.events;

/**
 * Integration event published by the {@code skinAnalysis} bounded context when a patient's
 * facial scan session has been successfully started.
 *
 * <p>This is the <em>published language</em> of the skin analysis context.
 * Other bounded contexts should listen to this event rather than to the internal
 * {@link com.bloomie.platform.skinanalysis.domain.model.events.FacialScanStartedEvent}.</p>
 *
 * @param facialScanId the persistence id of the started facial scan
 * @param patientId    the IAM user id of the patient
 */
public record FacialScanStartedIntegrationEvent(Long facialScanId, Long patientId) {
}
