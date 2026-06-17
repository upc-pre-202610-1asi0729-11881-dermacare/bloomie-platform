package com.bloomie.platform.skinanalysis.interfaces.events;

/**
 * Integration event published by the {@code skinAnalysis} bounded context when a patient's
 * skin profile has been successfully completed.
 *
 * <p>This is the <em>published language</em> of the skin analysis context.
 * Other bounded contexts should listen to this event rather than to the internal
 * {@link com.bloomie.platform.skinAnalysis.domain.model.events.SkinProfileCompletedEvent}.</p>
 *
 * @param skinProfileId the persistence id of the completed skin profile
 * @param patientId     the IAM user id of the patient
 * @param skinType      the reported skin type label (e.g. {@code "OILY"})
 */
public record SkinProfileCompletedIntegrationEvent(
        Long skinProfileId,
        Long patientId,
        String skinType) {
}
