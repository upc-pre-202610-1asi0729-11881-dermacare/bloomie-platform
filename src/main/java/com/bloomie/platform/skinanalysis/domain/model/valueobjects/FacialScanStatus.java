package com.bloomie.platform.skinanalysis.domain.model.valueobjects;

/**
 * Lifecycle status of a facial scan.
 *
 * <ul>
 *   <li>{@code STARTED}   – the scan session has been opened.</li>
 *   <li>{@code SUBMITTED} – the photo has been uploaded and sent for analysis.</li>
 *   <li>{@code COMPLETED} – analysis results have been received.</li>
 *   <li>{@code FAILED}    – the scan or analysis encountered an error.</li>
 * </ul>
 */
public enum FacialScanStatus {
    STARTED,
    SUBMITTED,
    COMPLETED,
    FAILED
}
