package com.bloomie.platform.routinemanagement.application.queryservices;

import com.bloomie.platform.routinemanagement.domain.model.aggregates.Routine;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetAllRoutinesQuery;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetRoutineByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for routine read queries.
 */
public interface RoutineQueryService {

    /**
     * Handles retrieval of all routines.
     *
     * @param query query marker
     * @return list of all routines
     * @see GetAllRoutinesQuery
     */
    List<Routine> handle(GetAllRoutinesQuery query);

    /**
     * Handles retrieval of a routine by its unique identifier.
     *
     * @param query routine-id query
     * @return matching routine, if found
     * @see GetRoutineByIdQuery
     */
    Optional<Routine> handle(GetRoutineByIdQuery query);
}