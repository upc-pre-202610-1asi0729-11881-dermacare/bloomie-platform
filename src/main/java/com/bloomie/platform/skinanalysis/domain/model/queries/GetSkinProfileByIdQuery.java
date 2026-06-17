package com.bloomie.platform.skinanalysis.domain.model.queries;

/**
 * Query to retrieve a single skin profile by its persistence id.
 *
 * @param skinProfileId the id of the skin profile to retrieve
 */
public record GetSkinProfileByIdQuery(Long skinProfileId) {
}
