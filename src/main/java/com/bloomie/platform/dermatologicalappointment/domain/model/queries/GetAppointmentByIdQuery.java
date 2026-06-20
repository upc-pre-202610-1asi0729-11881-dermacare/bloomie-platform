package com.bloomie.platform.dermatologicalappointment.domain.model.queries;

/**
 * Query to retrieve a single appointment by its persistence id.
 *
 * @param appointmentId the id of the appointment to retrieve; must be a positive number
 */
public record GetAppointmentByIdQuery(Long appointmentId) {
}
