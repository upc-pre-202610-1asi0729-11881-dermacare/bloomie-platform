package com.bloomie.platform.skinanalysis.domain.model.valueobjects;

/**
 * Lifecycle status of a skin profile.
 *
 * <ul>
 *   <li>{@code PENDING}   – the profile has been initialised but not yet completed.</li>
 *   <li>{@code COMPLETED} – the patient finished the skin profile questionnaire.</li>
 * </ul>
 */
public enum SkinProfileStatus {
    PENDING,
    COMPLETED
}
