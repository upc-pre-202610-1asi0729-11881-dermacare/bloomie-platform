package com.bloomie.platform.skinanalysis.domain.repositories;

import com.bloomie.platform.skinanalysis.domain.model.aggregates.SkinProfile;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.PatientId;

import java.util.Optional;

/**
 * Domain repository port for the {@link SkinProfile} aggregate.
 *
 * <p>Defines the persistence contract without any JPA or Spring Data dependency.
 * All methods use domain types — no persistence entities cross this boundary.</p>
 */
public interface SkinProfileRepository {

    Optional<SkinProfile> findById(Long id);

    Optional<SkinProfile> findByPatientId(PatientId patientId);

    boolean existsByPatientId(PatientId patientId);

    /**
     * Persists the skin profile and returns the saved instance.
     *
     * <p>For new aggregates ({@code id == null}): the adapter saves the entity, calls
     * {@code onCompleted()} on the reconstructed aggregate, publishes events and clears them.
     * For updates: calls {@code onUpdated()} on the reconstructed aggregate,
     * publishes events and clears them.</p>
     */
    SkinProfile save(SkinProfile skinProfile);
}
