package com.bloomie.platform.skinanalysis.interfaces.events;

/**
 * Integration event published by the {@code skinAnalysis} bounded context when the
 * skin characteristics of an existing profile have been updated.
 *
 * <p>This is the <em>published language</em> of the skin analysis context.
 * Other bounded contexts should listen to this event rather than to the internal
 * {@link com.bloomie.platform.skinAnalysis.domain.model.events.SkinCharacteristicsUpdatedEvent}.</p>
 *
 * @param skinProfileId the persistence id of the updated skin profile
 * @param patientId     the IAM user id of the patient
 * @param skinType      the new skin type label (e.g. {@code "OILY"})
 */
public record SkinCharacteristicsUpdatedIntegrationEvent(
        Long skinProfileId,
        Long patientId,
        String skinType) {
}
