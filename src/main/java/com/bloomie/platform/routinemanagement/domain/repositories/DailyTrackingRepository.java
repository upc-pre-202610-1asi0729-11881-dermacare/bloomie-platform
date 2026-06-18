package com.bloomie.platform.routinemanagement.domain.repositories;

import com.bloomie.platform.routinemanagement.domain.model.aggregates.DailyTracking;

import java.util.List;

/**
 * Routine management daily tracking repository port.
 */
public interface DailyTrackingRepository {

    /**
     * Retrieves all daily tracking entries.
     *
     * @return list of all daily tracking entries
     */
    List<DailyTracking> findAll();
}