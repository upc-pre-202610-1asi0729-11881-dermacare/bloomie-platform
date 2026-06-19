package com.bloomie.platform.routinemanagement.application.queryservices;

import com.bloomie.platform.routinemanagement.domain.model.aggregates.DailyTracking;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetAllDailyTrackingsQuery;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetDailyTrackingByPatientIdAndDateQuery;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetDailyTrackingsByPatientIdQuery;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetDailyTrackingsByRoutineIdQuery;

import java.util.List;
import java.util.Optional;

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

    /**
     * Handles retrieval of a daily tracking entry for a patient on a specific date.
     *
     * @param query query containing the patient id and date
     * @return an {@link Optional} containing the tracking entry, or empty if not found
     * @see GetDailyTrackingByPatientIdAndDateQuery
     */
    Optional<DailyTracking> handle(GetDailyTrackingByPatientIdAndDateQuery query);

    /**
     * Handles retrieval of all daily tracking entries for a patient.
     *
     * @param query query containing the patient id
     * @return list of daily tracking entries for the given patient
     * @see GetDailyTrackingsByPatientIdQuery
     */
    List<DailyTracking> handle(GetDailyTrackingsByPatientIdQuery query);

    /**
     * Handles retrieval of all daily tracking entries for a routine.
     *
     * @param query query containing the routine id
     * @return list of daily tracking entries for the given routine
     * @see GetDailyTrackingsByRoutineIdQuery
     */
    List<DailyTracking> handle(GetDailyTrackingsByRoutineIdQuery query);
}
