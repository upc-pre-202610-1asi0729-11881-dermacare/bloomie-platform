package com.bloomie.platform.dermatologicalappointment.domain.model.events;

import com.bloomie.platform.dermatologicalappointment.domain.model.aggregates.Appointment;

/**
 * Domain event raised when a dermatological appointment has been rescheduled to a new date.
 *
 * @param appointmentId   the id of the reprogrammed appointment
 * @param patientId       the IAM user id of the patient
 * @param dermatologistId the IAM user id of the dermatologist
 * @param newScheduledAt  ISO-8601 string of the new appointment date-time
 */
public record AppointmentReprogrammedEvent(
        Long appointmentId,
        Long patientId,
        Long dermatologistId,
        String newScheduledAt) {

    public static AppointmentReprogrammedEvent from(Appointment appointment) {
        return new AppointmentReprogrammedEvent(
                appointment.getId(),
                appointment.getPatientId().patientId(),
                appointment.getDermatologistId().dermatologistId(),
                appointment.getScheduledAt().value());
    }
}
