package com.bloomie.platform.skinanalysis.domain.model.commands;

/**
 * Command to complete the skin profile for a patient.
 *
 * <p>Issued by the patient after filling in the skin questionnaire.
 * On successful handling the skin profile is created in status {@code COMPLETED}
 * and {@code SkinProfileCompletedEvent} is raised.</p>
 *
 * @param patientId   the IAM user id of the patient completing the profile
 * @param skinType    the patient's skin type (e.g. {@code OILY}, {@code DRY})
 * @param sensitivity the patient's skin sensitivity level (e.g. {@code LOW}, {@code HIGH})
 * @param waterIntake daily water intake range (e.g. {@code "3-5 glasses"})
 * @param sunExposure daily sun exposure range (e.g. {@code "30-60 minutes"})
 * @param sleepHours  daily sleep range (e.g. {@code "8 hours"})
 */
public record CompleteSkinProfileCommand(
        Long patientId,
        String skinType,
        String sensitivity,
        String waterIntake,
        String sunExposure,
        String sleepHours) {
}
