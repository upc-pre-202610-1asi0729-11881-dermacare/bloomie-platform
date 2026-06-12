package com.bloomie.platform.dermatologicalAppointment.domain.model.queries;

/**
 * Query to retrieve all appointments assigned to a specific dermatologist.
 *
 * @param dermatologistId the IAM user id of the dermatologist whose appointments are requested
 */
public record GetAppointmentsByDermatologistIdQuery(Long dermatologistId) {
}
