package com.bloomie.platform.dermatologicalAppointment.domain.model.events;

import com.bloomie.platform.dermatologicalAppointment.domain.model.aggregates.Appointment;

/**
 * Domain event raised when a patient has confirmed their dermatological appointment.
 *
 * @param appointmentId   the id of the confirmed appointment
 * @param patientId       the IAM user id of the patient
 * @param dermatologistId the IAM user id of the dermatologist
 * @param scheduledAt     ISO-8601 string of the confirmed appointment date-time
 */
public record AppointmentConfirmedEvent(
        Long appointmentId,
        Long patientId,
        Long dermatologistId,
        String scheduledAt) {

    public static AppointmentConfirmedEvent from(Appointment appointment) {
        return new AppointmentConfirmedEvent(
                appointment.getId(),
                appointment.getPatientId().patientId(),
                appointment.getDermatologistId().dermatologistId(),
                appointment.getScheduledAt().value());
    }
}
