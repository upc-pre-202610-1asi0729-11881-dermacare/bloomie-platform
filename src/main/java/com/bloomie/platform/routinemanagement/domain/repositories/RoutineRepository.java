package com.bloomie.platform.routinemanagement.domain.repositories;

import com.bloomie.platform.routinemanagement.domain.model.aggregates.Routine;
import com.bloomie.platform.routinemanagement.domain.model.valueobjects.PatientId;

import java.util.Optional;

/**
 * Routine management routine repository port.
 */
public interface RoutineRepository {

    /**
     * Retrieves a routine by its unique identifier.
     *
     * @param id the routine identifier
     * @return the matching routine, if found
     */
    Optional<Routine> findById(Long id);

    /**
     * Retrieves the active routine for a given patient.
     *
     * <p>Only returns a routine whose status is {@code ACTIVE}. A patient may have
     * multiple historical (inactive) routines; this method always returns at most one.</p>
     *
     * @param patientId the patient value object
     * @return the active routine, if one exists
     */
    Optional<Routine> findActiveByPatientId(PatientId patientId);

    /**
     * Persists a routine (create or update).
     *
     * @param routine the routine to save
     * @return the saved routine with its generated identifier
     */
    Routine save(Routine routine);

    /**
     * Checks whether an active routine exists for the given patient.
     *
     * @param patientId the patient value object
     * @return true if an active routine exists
     */
    boolean existsByPatientId(PatientId patientId);
}