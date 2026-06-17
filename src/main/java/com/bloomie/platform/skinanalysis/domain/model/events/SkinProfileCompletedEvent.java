package com.bloomie.platform.skinanalysis.domain.model.events;

import com.bloomie.platform.skinanalysis.domain.model.aggregates.SkinProfile;

/**
 * Domain event raised when a patient's skin profile has been successfully completed.
 *
 * <p>Consumed by {@code SkinProfileCompletedEventHandler}, which re-publishes it as a
 * {@link com.bloomie.platform.skinAnalysis.interfaces.events.SkinProfileCompletedIntegrationEvent}.</p>
 *
 * @param skinProfileId the id of the completed skin profile
 * @param patientId     the IAM user id of the patient
 * @param skinType      the reported skin type label
 */
public record SkinProfileCompletedEvent(
        Long skinProfileId,
        Long patientId,
        String skinType) {

    /** Factory method to build the event from the saved aggregate. */
    public static SkinProfileCompletedEvent from(SkinProfile skinProfile) {
        return new SkinProfileCompletedEvent(
                skinProfile.getId(),
                skinProfile.getPatientId().patientId(),
                skinProfile.getSkinType().name());
    }
}
