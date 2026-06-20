package com.bloomie.platform.routinemanagement.interfaces.rest.transform;

import com.bloomie.platform.routinemanagement.domain.model.aggregates.DailyTracking;
import com.bloomie.platform.routinemanagement.interfaces.rest.resources.DailyTrackingResource;

/**
 * Assembler that converts a {@link DailyTracking} domain aggregate into a {@link DailyTrackingResource}.
 */
public final class DailyTrackingResourceFromEntityAssembler {

    private DailyTrackingResourceFromEntityAssembler() {
    }

    /**
     * Converts a daily tracking aggregate into its REST resource representation.
     *
     * @param tracking the domain aggregate to convert
     * @return the {@link DailyTrackingResource} for the REST response
     */
    public static DailyTrackingResource toResourceFromEntity(DailyTracking tracking) {
        var date = tracking.getDate() != null ? tracking.getDate().toString() : null;
        var completedAt = tracking.getCompletedAt() != null ? tracking.getCompletedAt().toString() : null;
        return new DailyTrackingResource(
                tracking.getId(),
                tracking.getPatientId().patientId(),
                tracking.getRoutineId().routineId(),
                date,
                tracking.isCompleted(),
                completedAt);
    }
}
