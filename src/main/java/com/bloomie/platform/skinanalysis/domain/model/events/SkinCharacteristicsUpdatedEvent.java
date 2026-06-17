package com.bloomie.platform.skinanalysis.domain.model.events;

import com.bloomie.platform.skinanalysis.domain.model.aggregates.SkinProfile;

/**
 * Domain event raised when the skin characteristics of an existing profile have been updated.
 *
 * <p>Consumed by {@code SkinCharacteristicsUpdatedEventHandler}, which re-publishes it as a
 * {@link com.bloomie.platform.skinAnalysis.interfaces.events.SkinCharacteristicsUpdatedIntegrationEvent}.</p>
 *
 * @param skinProfileId the id of the updated skin profile
 * @param patientId     the IAM user id of the patient
 * @param skinType      the new skin type label
 */
public record SkinCharacteristicsUpdatedEvent(
        Long skinProfileId,
        Long patientId,
        String skinType) {

    /** Factory method to build the event from the saved aggregate. */
    public static SkinCharacteristicsUpdatedEvent from(SkinProfile skinProfile) {
        return new SkinCharacteristicsUpdatedEvent(
                skinProfile.getId(),
                skinProfile.getPatientId().patientId(),
                skinProfile.getSkinType().name());
    }
}
