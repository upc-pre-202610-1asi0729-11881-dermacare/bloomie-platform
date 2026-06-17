package com.bloomie.platform.skinanalysis.domain.model.queries;

/**
 * Query to retrieve a single facial scan by its persistence id.
 *
 * @param facialScanId the id of the facial scan to retrieve
 */
public record GetFacialScanByIdQuery(Long facialScanId) {
}
