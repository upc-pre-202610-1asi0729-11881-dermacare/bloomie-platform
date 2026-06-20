package com.bloomie.platform.routinemanagement.interfaces.events;

/**
 * Integration event published by the {@code routineManagement} bounded context when a personalized
 * routine has been generated for a patient.
 *
 * <p>This is the <em>published language</em> of the routine management context.
 * Other bounded contexts should listen to this event rather than to the internal
 * {@link com.bloomie.platform.routinemanagement.domain.model.events.PersonalizedRoutineGeneratedEvent}.</p>
 *
 * @param routineId      the persistence id of the generated routine
 * @param patientId      the IAM user id of the patient
 * @param skinAnalysisId the id of the skin analysis that triggered the routine
 */
public record PersonalizedRoutineGeneratedIntegrationEvent(Long routineId, Long patientId, Long skinAnalysisId) {
}