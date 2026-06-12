package com.bloomie.platform.dermatologicalAppointment.domain.model.commands;

/**
 * Command to schedule a new dermatological appointment.
 *
 * <p>Issued by the patient from the Dermatologist List View after selecting a dermatologist
 * and a desired time slot. On successful handling, the appointment is created in
 * {@code SCHEDULED} status and {@code DermatologyAppointmentScheduledEvent} is raised.</p>
 *
 * @param patientId       the IAM user id of the patient scheduling the appointment
 * @param dermatologistId the IAM user id of the chosen dermatologist
 * @param scheduledAt     ISO-8601 local date-time string (e.g. {@code "2025-12-25T10:00:00"});
 *                        must represent a future instant
 */
public record ScheduleDermatologyAppointmentCommand(
        Long patientId,
        Long dermatologistId,
        String scheduledAt) {
}
