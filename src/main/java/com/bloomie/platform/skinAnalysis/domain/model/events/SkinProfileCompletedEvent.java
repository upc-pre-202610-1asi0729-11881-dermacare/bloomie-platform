package com.bloomie.platform.skinAnalysis.domain.model.events;

import com.bloomie.platform.skinAnalysis.domain.model.aggregates.SkinProfile;

import java.util.List;

/**
 * Domain event raised when a patient's skin profile has been successfully completed.
 *
 * <p>Can be consumed by downstream policies (e.g. product-recommendation engine)
 * that react to newly available profile data.</p>
 *
 * @param skin_profile_id the id of the completed skin profile
 * @param patient_id      the IAM user id of the patient
 * @param skin_type       the reported skin type label
 * @param skin_tone       the reported skin tone label
 * @param concerns        the list of skin concerns
 */
public record SkinProfileCompletedEvent(
        Long skin_profile_id,
        Long patient_id,
        String skin_type,
        String skin_tone,
        List<String> concerns) {

    public static SkinProfileCompletedEvent from(SkinProfile skinProfile) {
        return new SkinProfileCompletedEvent(
                skinProfile.getId(),
                skinProfile.getPatientId().patient_id(),
                skinProfile.getSkinType().name(),
                skinProfile.getSkinTone().name(),
                skinProfile.getSkinConcerns().concerns());
    }
}
