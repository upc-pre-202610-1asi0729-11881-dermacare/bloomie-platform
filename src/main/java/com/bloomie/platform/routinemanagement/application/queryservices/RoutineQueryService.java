package com.bloomie.platform.routinemanagement.application.queryservices;

import com.bloomie.platform.routinemanagement.domain.model.aggregates.Routine;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetRecommendedProductsForRoutineItemQuery;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetRoutineByIdQuery;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetRoutineByPatientIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for routine read queries.
 */
public interface RoutineQueryService {

    /**
     * Handles retrieval of a routine by its unique identifier.
     *
     * @param query routine-id query
     * @return matching routine, if found
     */
    Optional<Routine> handle(GetRoutineByIdQuery query);

    /**
     * Handles retrieval of a routine by patient identifier.
     *
     * @param query patient-id query
     * @return matching routine, if found
     */
    Optional<Routine> handle(GetRoutineByPatientIdQuery query);

    /**
     * Returns the AI-recommended product replacement options for the given routine item.
     *
     * @param query routine and item identifiers
     * @return ordered list of product name alternatives
     */
    List<String> handle(GetRecommendedProductsForRoutineItemQuery query);
}