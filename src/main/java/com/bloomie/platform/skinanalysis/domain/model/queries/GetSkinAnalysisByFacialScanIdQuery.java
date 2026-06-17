package com.bloomie.platform.skinanalysis.domain.model.queries;

import com.bloomie.platform.skinanalysis.domain.model.valueobjects.FacialScanId;

/**
 * Query to retrieve a skin analysis by its associated facial scan.
 *
 * @param facialScanId the facial scan value object
 */
public record GetSkinAnalysisByFacialScanIdQuery(FacialScanId facialScanId) {
}
