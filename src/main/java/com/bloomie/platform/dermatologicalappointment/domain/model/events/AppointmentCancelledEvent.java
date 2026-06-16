package com.bloomie.platform.dermatologicalappointment.domain.model.events;

import com.bloomie.platform.dermatologicalappointment.domain.model.aggregates.Appointment;

/**
 * Domain event raised when a dermatological appointment has been cancelled by the patient.
 *
 * <p>Consumed by the {@code AppointmentCancelledEventHandler} policy, which calls
 * {@code isEligibleForRefund()} on the appointment and, if eligible, publishes a
 * {@code ProcessRefundIntegrationEvent} to the Subscription BC.</p>
 *
 * @param appointmentId      the id of the cancelled appointment
 * @param patientId          the IAM user id of the patient
 * @param dermatologistId    the IAM user id of the dermatologist
 * @param scheduledAt        ISO-8601 string of the original scheduled date-time (for eligibility check)
 * @param cancellationReason the reason provided by the patient
 */
public record AppointmentCancelledEvent(
        Long appointmentId,
        Long patientId,
        Long dermatologistId,
        String scheduledAt,
        String cancellationReason) {

    public static AppointmentCancelledEvent from(Appointment appointment) {
        return new AppointmentCancelledEvent(
                appointment.getId(),
                appointment.getPatientId().patientId(),
                appointment.getDermatologistId().dermatologistId(),
                appointment.getScheduledAt().value(),
                appointment.getCancellationReason());
    }
}
