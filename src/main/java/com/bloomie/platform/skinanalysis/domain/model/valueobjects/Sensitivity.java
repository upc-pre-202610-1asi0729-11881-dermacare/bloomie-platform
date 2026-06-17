package com.bloomie.platform.skinanalysis.domain.model.valueobjects;

/**
 * Skin sensitivity level reported by the patient.
 *
 * <ul>
 *   <li>{@code LOW}    – rarely reacts to products or environment.</li>
 *   <li>{@code MEDIUM} – occasional reactions to certain ingredients.</li>
 *   <li>{@code HIGH}   – frequent irritation, redness, or burning sensations.</li>
 * </ul>
 */
public enum Sensitivity {
    LOW,
    MEDIUM,
    HIGH
}
