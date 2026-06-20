package com.bloomie.platform.skinanalysis.domain.model.valueobjects;

/**
 * Value object that holds the identity of a {@code FacialScan} aggregate.
 *
 * @param facialScanId the persistence id of the facial scan; must be a positive number
 */
public record FacialScanId(Long facialScanId) {

    private static final String INVALID_MESSAGE_KEY = "skin.facial.scan.id.invalid";

    public FacialScanId {
        if (facialScanId == null || facialScanId < 1) {
            throw new IllegalArgumentException(INVALID_MESSAGE_KEY);
        }
    }
}
