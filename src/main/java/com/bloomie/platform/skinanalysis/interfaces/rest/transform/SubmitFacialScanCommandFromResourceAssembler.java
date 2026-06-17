package com.bloomie.platform.skinanalysis.interfaces.rest.transform;

import com.bloomie.platform.skinanalysis.domain.model.commands.SubmitFacialScanCommand;
import com.bloomie.platform.skinanalysis.interfaces.rest.resources.SubmitFacialScanResource;

/**
 * Converts a {@link SubmitFacialScanResource} request body into a {@link SubmitFacialScanCommand}.
 */
public final class SubmitFacialScanCommandFromResourceAssembler {

    private SubmitFacialScanCommandFromResourceAssembler() {}

    public static SubmitFacialScanCommand toCommandFromResource(Long facialScanId, SubmitFacialScanResource resource) {
        return new SubmitFacialScanCommand(facialScanId, resource.photoUrl());
    }
}
