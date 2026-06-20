package com.bloomie.platform.skinanalysis.interfaces.rest.transform;

import com.bloomie.platform.skinanalysis.domain.model.aggregates.FacialScan;
import com.bloomie.platform.skinanalysis.interfaces.rest.resources.FacialScanResource;

/**
 * Converts a {@link FacialScan} aggregate into a {@link FacialScanResource} response.
 */
public final class FacialScanResourceFromEntityAssembler {

    private FacialScanResourceFromEntityAssembler() {}

    public static FacialScanResource toResourceFromEntity(FacialScan facialScan) {
        return new FacialScanResource(
                facialScan.getId(),
                facialScan.getPatientId(),
                facialScan.getStatus().name(),
                facialScan.getPhotoUrl(),
                facialScan.getScannedAt() != null ? facialScan.getScannedAt().toString() : null);
    }
}
