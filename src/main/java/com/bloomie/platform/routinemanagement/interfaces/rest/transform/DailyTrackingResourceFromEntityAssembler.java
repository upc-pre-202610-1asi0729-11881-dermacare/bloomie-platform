package com.bloomie.platform.routinemanagement.interfaces.rest.transform;

import com.bloomie.platform.routinemanagement.domain.model.aggregates.DailyTracking;
import com.bloomie.platform.routinemanagement.interfaces.rest.resources.DailyTrackingResource;

/**
 * Assembler that converts a {@link DailyTracking} domain aggregate into a {@link DailyTrackingResource}.
 */
public final class DailyTrackingResourceFromEntityAssembler {

    private DailyTrackingResourceFromEntityAssembler() {
    }

    public static DailyTrackingResource toResourceFromEntity(DailyTracking tracking) {
        var date = tracking.getDate() != null
                ? tracking.getDate().toString()
                : null;
        return new DailyTrackingResource(
                tracking.getId(),
                tracking.getRoutineId(),
                tracking.getUserId(),
                date,
                tracking.getStatus().name()
        );
    }
}