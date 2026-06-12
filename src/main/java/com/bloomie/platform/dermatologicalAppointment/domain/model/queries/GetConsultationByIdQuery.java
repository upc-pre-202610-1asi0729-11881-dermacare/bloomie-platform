package com.bloomie.platform.dermatologicalAppointment.domain.model.queries;

/**
 * Query to retrieve a single consultation by its persistence id.
 *
 * @param consultationId the id of the consultation to retrieve
 */
public record GetConsultationByIdQuery(Long consultationId) {
}
