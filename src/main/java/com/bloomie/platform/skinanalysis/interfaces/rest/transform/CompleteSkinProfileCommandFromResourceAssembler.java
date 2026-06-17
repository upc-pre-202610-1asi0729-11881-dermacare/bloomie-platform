package com.bloomie.platform.skinAnalysis.interfaces.rest.transform;

import com.bloomie.platform.skinAnalysis.domain.model.commands.CompleteSkinProfileCommand;
import com.bloomie.platform.skinAnalysis.interfaces.rest.resources.CompleteSkinProfileResource;

public final class CompleteSkinProfileCommandFromResourceAssembler {

    private CompleteSkinProfileCommandFromResourceAssembler() {}

    public static CompleteSkinProfileCommand toCommandFromResource(CompleteSkinProfileResource resource) {
        return new CompleteSkinProfileCommand(
                resource.patient_id(),
                resource.skin_type(),
                resource.skin_tone(),
                resource.concerns());
    }
}
