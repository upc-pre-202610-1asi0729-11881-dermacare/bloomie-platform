package com.bloomie.platform.dermatologicalAppointment.domain.model.events;

import com.bloomie.platform.dermatologicalAppointment.domain.model.aggregates.Appointment;

/**
 * Domain event raised when a new dermatological appointment has been successfully scheduled.
 *
 * <p>Consumed by the {@code DermatologyAppointmentScheduledEventHandler} policy, which translates
 * it into a {@code RequestConsultationPaymentIntegrationEvent} for the Subscription BC.</p>
 *
 * @param appointmentId   the id of the newly scheduled appointment
 * @param patientId       the IAM user id of the patient
 * @param dermatologistId the IAM user id of the dermatologist
 * @param scheduledAt     ISO-8601 string of the scheduled date-time
 */
public record DermatologyAppointmentScheduledEvent(
        Long appointmentId,
        Long patientId,
        Long dermatologistId,
        String scheduledAt) {

    public static DermatologyAppointmentScheduledEvent from(Appointment appointment) {
        return new DermatologyAppointmentScheduledEvent(
                appointment.getId(),
                appointment.getPatientId().patientId(),
                appointment.getDermatologistId().dermatologistId(),
                appointment.getScheduledAt().value());
    }
}
