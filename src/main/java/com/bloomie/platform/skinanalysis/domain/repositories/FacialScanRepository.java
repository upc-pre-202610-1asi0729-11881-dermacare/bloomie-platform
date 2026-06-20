package com.bloomie.platform.skinanalysis.domain.repositories;

import com.bloomie.platform.skinanalysis.domain.model.aggregates.FacialScan;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.PatientId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository port for the {@link FacialScan} aggregate.
 *
 * <p>Defines the persistence contract without any JPA or Spring Data dependency.
 * All methods use domain types — no persistence entities cross this boundary.</p>
 */
public interface FacialScanRepository {

    Optional<FacialScan> findById(Long id);

    List<FacialScan> findAllByPatientId(PatientId patientId);

    /**
     * Persists the facial scan and returns the saved instance.
     *
     * <p>For new aggregates ({@code id == null}): the adapter saves the entity, calls
     * {@code onStarted()} on the reconstructed aggregate, publishes events and clears them.</p>
     */
    FacialScan save(FacialScan facialScan);
}
