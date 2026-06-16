package com.bloomie.platform.skinAnalysis.domain.model.valueobjects;

/**
 * Lifecycle status of a skin profile.
 *
 * <ul>
 *   <li>{@code COMPLETED} – the patient finished the skin profile questionnaire.</li>
 *   <li>{@code PENDING}   – the profile has been initialised but not yet completed.</li>
 * </ul>
 */
public enum SkinProfileStatus {
    PENDING,
    COMPLETED
}
