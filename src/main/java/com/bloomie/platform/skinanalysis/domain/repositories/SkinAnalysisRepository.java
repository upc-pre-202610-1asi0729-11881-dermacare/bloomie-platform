package com.bloomie.platform.skinanalysis.domain.repositories;

import com.bloomie.platform.skinanalysis.domain.model.aggregates.SkinAnalysis;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.FacialScanId;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.PatientId;

import java.util.List;
import java.util.Optional;

/**
 * Repository port for {@link SkinAnalysis} aggregate persistence operations.
 *
 * <p>The infrastructure layer provides the concrete JPA adapter implementation.</p>
 */
public interface SkinAnalysisRepository {

    /**
     * Returns the skin analysis with the given id, or empty if not found.
     *
     * @param id the persistence id
     * @return an optional containing the skin analysis
     */
    Optional<SkinAnalysis> findById(Long id);

    /**
     * Returns the skin analysis associated with the given facial scan, or empty if none exists.
     *
     * @param facialScanId the facial scan value object
     * @return an optional containing the skin analysis
     */
    Optional<SkinAnalysis> findByFacialScanId(FacialScanId facialScanId);

    /**
     * Returns all skin analyses belonging to the given patient.
     *
     * @param patientId the patient value object
     * @return a list of skin analyses (may be empty)
     */
    List<SkinAnalysis> findAllByPatientId(PatientId patientId);

    /**
     * Persists the given skin analysis and publishes domain events.
     *
     * @param skinAnalysis the aggregate to save
     * @return the saved aggregate with its persistence id assigned
     */
    SkinAnalysis save(SkinAnalysis skinAnalysis);
}
