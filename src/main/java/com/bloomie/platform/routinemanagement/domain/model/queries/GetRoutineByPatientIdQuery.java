package com.bloomie.platform.routinemanagement.domain.model.queries;

/**
 * Query to retrieve a routine by patient identifier.
 *
 * @param patientId the patient identifier
 */
public record GetRoutineByPatientIdQuery(Long patientId) {
}