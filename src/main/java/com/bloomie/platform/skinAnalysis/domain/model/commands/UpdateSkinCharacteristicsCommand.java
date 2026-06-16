package com.bloomie.platform.skinAnalysis.domain.model.commands;

import java.util.List;

/**
 * Command to update the skin characteristics of an existing skin profile.
 *
 * <p>Issued by the patient from the Skin Profile Summary View.
 * On successful handling the skin profile characteristics are updated and
 * {@code SkinCharacteristicsUpdatedEvent} is raised.</p>
 *
 * @param skin_profile_id the id of the skin profile to update
 * @param skin_type       the new skin type (e.g. {@code OILY}, {@code DRY})
 * @param skin_tone       the new skin tone (e.g. {@code FAIR}, {@code MEDIUM})
 * @param concerns        the updated list of skin concerns
 */
public record UpdateSkinCharacteristicsCommand(
        Long skin_profile_id,
        String skin_type,
        String skin_tone,
        List<String> concerns) {
}