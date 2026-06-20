package com.bloomie.platform.dermatologicalappointment.interfaces.events;

/**
 * Integration event published when a new appointment is scheduled and the consultation payment
 * must be processed. Consumed by the Subscription bounded context.
 *
 * @param appointmentId   the id of the scheduled appointment
 * @param patientId       the IAM user id of the patient
 * @param dermatologistId the IAM user id of the dermatologist
 * @param scheduledAt     ISO-8601 string of the scheduled date-time
 */
public record RequestConsultationPaymentIntegrationEvent(
        Long appointmentId,
        Long patientId,
        Long dermatologistId,
        String scheduledAt) {
}
