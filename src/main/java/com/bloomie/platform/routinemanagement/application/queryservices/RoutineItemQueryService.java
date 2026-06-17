package com.bloomie.platform.routinemanagement.application.queryservices;

import com.bloomie.platform.routinemanagement.domain.model.entities.RoutineItem;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetAllRoutineItemsQuery;

import java.util.List;

/**
 * Application service contract for routine item read queries.
 */
public interface RoutineItemQueryService {

    /**
     * Handles retrieval of all routine items.
     *
     * @param query query marker
     * @return list of all routine items
     * @see GetAllRoutineItemsQuery
     */
    List<RoutineItem> handle(GetAllRoutineItemsQuery query);
}