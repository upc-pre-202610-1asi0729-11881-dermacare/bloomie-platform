package com.bloomie.platform.dermatologicalAppointment.domain.model.queries;

/**
 * Query to retrieve all appointments belonging to a specific patient.
 *
 * @param patientId the IAM user id of the patient whose appointments are requested
 */
public record GetAppointmentsByPatientIdQuery(Long patientId) {
}
