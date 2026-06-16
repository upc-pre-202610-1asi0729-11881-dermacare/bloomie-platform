package com.bloomie.platform.dermatologicalAppointment.domain.model.queries;

/**
 * Query to retrieve the consultation associated with a specific appointment.
 *
 * <p>Each appointment can have at most one consultation, so this query returns an
 * {@link java.util.Optional} at the service level.</p>
 *
 * @param appointmentId the id of the appointment whose consultation is requested
 */
public record GetConsultationByAppointmentIdQuery(Long appointmentId) {
}
