package com.bloomie.platform.dermatologicalappointment.domain.model.events;

import com.bloomie.platform.dermatologicalappointment.domain.model.aggregates.Appointment;

/**
 * Domain event raised when an appointment transitions to {@code IN_PROGRESS}
 * because its consultation has started.
 *
 * @param appointmentId   the id of the appointment
 * @param patientId       the IAM user id of the patient
 * @param dermatologistId the IAM user id of the dermatologist
 * @param scheduledAt     ISO-8601 string of the appointment date-time
 */
public record AppointmentMarkedInProgressEvent(
        Long appointmentId,
        Long patientId,
        Long dermatologistId,
        String scheduledAt) {

    public static AppointmentMarkedInProgressEvent from(Appointment appointment) {
        return new AppointmentMarkedInProgressEvent(
                appointment.getId(),
                appointment.getPatientId().patientId(),
                appointment.getDermatologistId().dermatologistId(),
                appointment.getScheduledAt().value());
    }
}
