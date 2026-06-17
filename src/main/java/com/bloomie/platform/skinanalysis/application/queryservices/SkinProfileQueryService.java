package com.bloomie.platform.skinAnalysis.application.queryservices;

import com.bloomie.platform.skinAnalysis.domain.model.aggregates.SkinProfile;
import com.bloomie.platform.skinAnalysis.domain.model.queries.GetSkinProfileByIdQuery;
import com.bloomie.platform.skinAnalysis.domain.model.queries.GetSkinProfileByPatientIdQuery;

import java.util.Optional;

/**
 * Application service interface for all read operations on the {@link SkinProfile} aggregate.
 *
 * <p>This interface is the public contract of the query side.</p>
 */
public interface SkinProfileQueryService {

    /**
     * Returns the skin profile with the given id, or empty if not found.
     *
     * @param query the query carrying the skin profile id
     * @return an optional containing the skin profile
     */
    Optional<SkinProfile> handle(GetSkinProfileByIdQuery query);

    /**
     * Returns the skin profile belonging to the given patient, or empty if not found.
     *
     * @param query the query carrying the patient's IAM id
     * @return an optional containing the skin profile
     */
    Optional<SkinProfile> handle(GetSkinProfileByPatientIdQuery query);
}