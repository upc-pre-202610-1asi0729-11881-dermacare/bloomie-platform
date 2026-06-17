package com.bloomie.platform.skinAnalysis.domain.model.queries;

/**
 * Query to retrieve a single skin profile by its persistence id.
 *
 * @param skin_profile_id the id of the skin profile to retrieve; must be a positive number
 */
public record GetSkinProfileByIdQuery(Long skin_profile_id) {
}