package com.bloomie.platform.routinemanagement.application.queryservices;

import com.bloomie.platform.routinemanagement.domain.model.aggregates.DailyTracking;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetAllDailyTrackingsQuery;

import java.util.List;

/**
 * Application service contract for daily tracking read queries.
 */
public interface DailyTrackingQueryService {

    /**
     * Handles retrieval of all daily tracking entries.
     *
     * @param query query marker
     * @return list of all daily tracking entries
     * @see GetAllDailyTrackingsQuery
     */
    List<DailyTracking> handle(GetAllDailyTrackingsQuery query);
}