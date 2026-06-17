package com.bloomie.platform.skinanalysis.interfaces.rest.transform;

import com.bloomie.platform.skinanalysis.domain.model.commands.UpdateSkinCharacteristicsCommand;
import com.bloomie.platform.skinanalysis.interfaces.rest.resources.UpdateSkinCharacteristicsResource;

/**
 * Converts an {@link UpdateSkinCharacteristicsResource} request body into an
 * {@link UpdateSkinCharacteristicsCommand}.
 */
public final class UpdateSkinCharacteristicsCommandFromResourceAssembler {

    private UpdateSkinCharacteristicsCommandFromResourceAssembler() {}

    public static UpdateSkinCharacteristicsCommand toCommandFromResource(
            Long skinProfileId, UpdateSkinCharacteristicsResource resource) {
        return new UpdateSkinCharacteristicsCommand(
                skinProfileId,
                resource.skinType(),
                resource.sensitivity(),
                resource.waterIntake(),
                resource.sunExposure(),
                resource.sleepHours());
    }
}
