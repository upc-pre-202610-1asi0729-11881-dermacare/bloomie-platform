package com.bloomie.platform.routinemanagement.domain.model.events;

import com.bloomie.platform.routinemanagement.domain.model.aggregates.Routine;

/**
 * Domain event published when an optional product step is successfully removed from a {@link Routine}.
 *
 * <p>This event is internal to the Routine Management bounded context.
 * No external bounded context listens to it and no integration event is derived from it.</p>
 *
 * @param routineId      the identity of the routine from which the item was removed
 * @param patientId      the identity of the patient who owns the routine
 * @param routineItemId  the identity of the removed routine item
 * @param removedProduct the name of the product recommendation that was removed
 */
public record ProductRemovedFromRoutineEvent(
        Long routineId,
        Long patientId,
        Long routineItemId,
        String removedProduct) {

    /**
     * Factory method that builds the event from the affected aggregate.
     *
     * @param routine        the routine aggregate after the removal
     * @param routineItemId  the identity of the removed item
     * @param removedProduct the product recommendation that was removed
     * @return a new {@link ProductRemovedFromRoutineEvent}
     */
    public static ProductRemovedFromRoutineEvent from(Routine routine, Long routineItemId,
                                                      String removedProduct) {
        return new ProductRemovedFromRoutineEvent(
                routine.getId(),
                routine.getPatientId(),
                routineItemId,
                removedProduct);
    }
}
