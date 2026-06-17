package com.bloomie.platform.skinanalysis.interfaces.rest.transform;

import com.bloomie.platform.skinanalysis.domain.model.commands.CompleteSkinProfileCommand;
import com.bloomie.platform.skinanalysis.interfaces.rest.resources.CompleteSkinProfileResource;

/**
 * Converts a {@link CompleteSkinProfileResource} request body into a {@link CompleteSkinProfileCommand}.
 */
public final class CompleteSkinProfileCommandFromResourceAssembler {

    private CompleteSkinProfileCommandFromResourceAssembler() {}

    public static CompleteSkinProfileCommand toCommandFromResource(CompleteSkinProfileResource resource) {
        return new CompleteSkinProfileCommand(
                resource.patientId(),
                resource.skinType(),
                resource.sensitivity(),
                resource.waterIntake(),
                resource.sunExposure(),
                resource.sleepHours());
    }
}
