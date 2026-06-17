package com.bloomie.platform.skinAnalysis.domain.model.commands;

import java.util.List;

/**
 * Command to complete the skin profile for a patient.
 *
 * <p>Issued by the patient after filling in the skin questionnaire.
 * On successful handling the skin profile is saved with status {@code COMPLETED}
 * and {@code SkinProfileCompletedEvent} is raised.</p>
 *
 * @param patient_id  the IAM user id of the patient completing the profile
 * @param skin_type   the patient's skin type (e.g. {@code OILY}, {@code DRY})
 * @param skin_tone   the patient's skin tone (e.g. {@code FAIR}, {@code MEDIUM})
 * @param concerns    the list of skin concerns the patient wants to address
 */
public record CompleteSkinProfileCommand(
        Long patient_id,
        String skin_type,
        String skin_tone,
        List<String> concerns) {
}
