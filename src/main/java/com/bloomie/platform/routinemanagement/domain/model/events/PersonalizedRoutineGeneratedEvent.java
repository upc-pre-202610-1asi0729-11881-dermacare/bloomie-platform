package com.bloomie.platform.routinemanagement.domain.model.events;

import com.bloomie.platform.routinemanagement.domain.model.aggregates.Routine;

/**
 * Domain event raised when a personalized routine has been generated for a patient.
 *
 * <p>Consumed by {@link com.bloomie.platform.routinemanagement.application.internal.eventhandlers.PersonalizedRoutineGeneratedEventHandler},
 * which re-publishes it as a
 * {@link com.bloomie.platform.routinemanagement.interfaces.events.PersonalizedRoutineGeneratedIntegrationEvent}.</p>
 *
 * @param routineId      the id of the generated routine
 * @param patientId      the IAM user id of the patient
 * @param skinAnalysisId the id of the skin analysis that triggered this routine
 */
public record PersonalizedRoutineGeneratedEvent(Long routineId, Long patientId, Long skinAnalysisId) {

    /** Factory method to build the event from the saved aggregate. */
    public static PersonalizedRoutineGeneratedEvent from(Routine routine) {
        return new PersonalizedRoutineGeneratedEvent(
                routine.getId(),
                routine.getPatientId(),
                routine.getSkinAnalysisId());
    }
}