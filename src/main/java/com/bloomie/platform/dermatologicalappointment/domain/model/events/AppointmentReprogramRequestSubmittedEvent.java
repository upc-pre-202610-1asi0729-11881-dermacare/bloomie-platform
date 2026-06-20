package com.bloomie.platform.dermatologicalappointment.domain.model.events;

import com.bloomie.platform.dermatologicalappointment.domain.model.aggregates.Appointment;

/**
 * Domain event raised when a patient submits a request to reschedule an appointment.
 *
 * <p>Consumed by the {@code AppointmentReprogramRequestSubmittedEventHandler} policy, which
 * validates the reprogram window using {@code currentScheduledAt}. If valid (more than 24 h
 * remain), the policy dispatches a {@link ReprogramAppointmentCommand} with {@code requestedDate}.</p>
 *
 * @param appointmentId    the id of the appointment to reschedule
 * @param patientId        the IAM user id of the requesting patient
 * @param requestedDate    ISO-8601 string of the proposed new date
 * @param currentScheduledAt ISO-8601 string of the current appointment date (window validation)
 */
public record AppointmentReprogramRequestSubmittedEvent(
        Long appointmentId,
        Long patientId,
        String requestedDate,
        String currentScheduledAt) {

    public static AppointmentReprogramRequestSubmittedEvent from(Appointment appointment) {
        return new AppointmentReprogramRequestSubmittedEvent(
                appointment.getId(),
                appointment.getPatientId().patientId(),
                appointment.getPendingReprogramDate(),
                appointment.getScheduledAt().value());
    }
}
