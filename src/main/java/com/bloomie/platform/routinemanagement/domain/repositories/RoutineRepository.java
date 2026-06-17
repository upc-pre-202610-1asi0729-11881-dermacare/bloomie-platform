package com.bloomie.platform.routinemanagement.domain.repositories;

import com.bloomie.platform.routinemanagement.domain.model.aggregates.Routine;

import java.util.List;
import java.util.Optional;

/**
 * Routine management routine repository port.
 */
public interface RoutineRepository {

    /**
     * Retrieves all routines.
     *
     * @return list of all routines
     */
    List<Routine> findAll();

    /**
     * Retrieves a routine by its unique identifier.
     *
     * @param id the routine identifier
     * @return the matching routine, if found
     */
    Optional<Routine> findById(Long id);
}