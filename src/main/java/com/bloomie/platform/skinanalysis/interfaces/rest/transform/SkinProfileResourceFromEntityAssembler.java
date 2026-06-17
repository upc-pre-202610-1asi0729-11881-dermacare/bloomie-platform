package com.bloomie.platform.skinanalysis.interfaces.rest.transform;

import com.bloomie.platform.skinanalysis.domain.model.aggregates.SkinProfile;
import com.bloomie.platform.skinanalysis.interfaces.rest.resources.SkinProfileResource;

/**
 * Converts a {@link SkinProfile} aggregate into a {@link SkinProfileResource} response.
 */
public final class SkinProfileResourceFromEntityAssembler {

    private SkinProfileResourceFromEntityAssembler() {}

    public static SkinProfileResource toResourceFromEntity(SkinProfile skinProfile) {
        return new SkinProfileResource(
                skinProfile.getId(),
                skinProfile.getPatientId().patientId(),
                skinProfile.getSkinType().name(),
                skinProfile.getSensitivity().name(),
                skinProfile.getWaterIntake(),
                skinProfile.getSunExposure(),
                skinProfile.getSleepHours(),
                skinProfile.getStatus().name());
    }
}
