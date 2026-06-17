package com.bloomie.platform.skinanalysis.interfaces.rest.transform;

import com.bloomie.platform.skinanalysis.domain.model.commands.StartFacialScanCommand;
import com.bloomie.platform.skinanalysis.interfaces.rest.resources.StartFacialScanResource;

/**
 * Converts a {@link StartFacialScanResource} request body into a {@link StartFacialScanCommand}.
 */
public final class StartFacialScanCommandFromResourceAssembler {

    private StartFacialScanCommandFromResourceAssembler() {}

    public static StartFacialScanCommand toCommandFromResource(StartFacialScanResource resource) {
        return new StartFacialScanCommand(resource.patientId());
    }
}
