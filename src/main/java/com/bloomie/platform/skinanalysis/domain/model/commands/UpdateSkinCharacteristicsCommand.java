package com.bloomie.platform.skinanalysis.domain.model.commands;

/**
 * Command to update the skin characteristics of an existing skin profile.
 *
 * <p>Issued by the patient from the Skin Profile Summary View.
 * On successful handling the characteristics are updated and
 * {@code SkinCharacteristicsUpdatedEvent} is raised.</p>
 *
 * @param skinProfileId the id of the skin profile to update
 * @param skinType      the new skin type (e.g. {@code OILY}, {@code DRY})
 * @param sensitivity   the new sensitivity level (e.g. {@code LOW}, {@code HIGH})
 * @param waterIntake   updated daily water intake range
 * @param sunExposure   updated daily sun exposure range
 * @param sleepHours    updated daily sleep range
 */
public record UpdateSkinCharacteristicsCommand(
        Long skinProfileId,
        String skinType,
        String sensitivity,
        String waterIntake,
        String sunExposure,
        String sleepHours) {
}
