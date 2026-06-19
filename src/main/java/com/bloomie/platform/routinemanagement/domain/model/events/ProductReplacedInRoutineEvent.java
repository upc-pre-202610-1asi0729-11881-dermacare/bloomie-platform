package com.bloomie.platform.routinemanagement.domain.model.events;

import com.bloomie.platform.routinemanagement.domain.model.aggregates.Routine;

public record ProductReplacedInRoutineEvent(
        Long routineId,
        Long patientId,
        Long routineItemId,
        String previousProduct,
        String newProduct) {

    public static ProductReplacedInRoutineEvent from(Routine routine, Long routineItemId,
                                                     String previousProduct, String newProduct) {
        return new ProductReplacedInRoutineEvent(
                routine.getId(),
                routine.getPatientId(),
                routineItemId,
                previousProduct,
                newProduct);
    }
}
