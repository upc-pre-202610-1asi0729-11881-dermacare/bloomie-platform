package com.bloomie.platform.routinemanagement.domain.model.events;

import com.bloomie.platform.routinemanagement.domain.model.aggregates.DailyTracking;

import java.time.LocalDate;

/**
 * Domain event published when a patient successfully marks their routine as completed for a given day.
 *
 * <p>This event is internal to the Routine Management bounded context.
 * No external bounded context listens to it and no integration event is derived from it.</p>
 *
 * @param dailyTrackingId the identity of the created daily tracking record
 * @param patientId       the identity of the patient who completed the routine
 * @param routineId       the identity of the completed routine
 * @param date            the date on which the routine was completed
 */
public record DailyRoutineCompletionRecordedEvent(
        Long dailyTrackingId,
        Long patientId,
        Long routineId,
        LocalDate date) {

    /**
     * Factory method that builds the event from the persisted aggregate.
     *
     * @param dailyTracking the daily tracking aggregate after it was saved
     * @return a new {@link DailyRoutineCompletionRecordedEvent}
     */
    public static DailyRoutineCompletionRecordedEvent from(DailyTracking dailyTracking) {
        return new DailyRoutineCompletionRecordedEvent(
                dailyTracking.getId(),
                dailyTracking.getPatientId().patientId(),
                dailyTracking.getRoutineId().routineId(),
                dailyTracking.getDate());
    }
}
