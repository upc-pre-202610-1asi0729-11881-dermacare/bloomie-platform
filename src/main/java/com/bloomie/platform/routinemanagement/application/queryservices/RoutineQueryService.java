package com.bloomie.platform.routinemanagement.application.queryservices;

import com.bloomie.platform.routinemanagement.domain.model.aggregates.Routine;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetAllRoutinesQuery;

import java.util.List;

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
}