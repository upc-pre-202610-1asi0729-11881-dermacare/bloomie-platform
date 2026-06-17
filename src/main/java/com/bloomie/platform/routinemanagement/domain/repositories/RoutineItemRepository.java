package com.bloomie.platform.routinemanagement.domain.repositories;

import com.bloomie.platform.routinemanagement.domain.model.entities.RoutineItem;

import java.util.List;

/**
 * Routine management routine item repository port.
 */
public interface RoutineItemRepository {

    /**
     * Retrieves all routine items.
     *
     * @return list of all routine items
     */
    List<RoutineItem> findAll();
}