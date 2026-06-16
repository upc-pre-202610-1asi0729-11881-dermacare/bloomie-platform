package com.bloomie.platform.skinAnalysis.interfaces.rest.transform;

import com.bloomie.platform.skinAnalysis.domain.model.aggregates.SkinProfile;
import com.bloomie.platform.skinAnalysis.interfaces.rest.resources.SkinProfileResource;

public final class SkinProfileResourceFromEntityAssembler {

    private SkinProfileResourceFromEntityAssembler() {}

    public static SkinProfileResource toResourceFromEntity(SkinProfile skinProfile) {
        return new SkinProfileResource(
                skinProfile.getId(),
                skinProfile.getPatientId().patient_id(),
                skinProfile.getSkinType().name(),
                skinProfile.getSkinTone().name(),
                skinProfile.getSkinConcerns().concerns(),
                skinProfile.getStatus().name());
    }
}
