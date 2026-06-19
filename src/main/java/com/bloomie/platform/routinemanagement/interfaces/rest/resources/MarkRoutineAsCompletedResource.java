package com.bloomie.platform.routinemanagement.interfaces.rest.resources;

/**
 * Request resource for marking a routine as completed on a specific date.
 *
 * @param patientId the patient identifier
 * @param routineId the routine identifier
 * @param date      the completion date in ISO 8601 format (e.g. "2026-06-19")
 */
public record MarkRoutineAsCompletedResource(Long patientId, Long routineId, String date) {
}
