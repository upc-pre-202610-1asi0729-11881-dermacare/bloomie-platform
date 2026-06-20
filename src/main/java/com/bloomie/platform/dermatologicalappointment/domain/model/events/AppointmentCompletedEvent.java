package com.bloomie.platform.dermatologicalappointment.domain.model.events;

import com.bloomie.platform.dermatologicalappointment.domain.model.aggregates.Appointment;

/**
 * Domain event raised when a dermatological appointment has been marked as completed
 * following the closure of its associated consultation.
 *
 * @param appointmentId   the id of the completed appointment
 * @param patientId       the IAM user id of the patient
 * @param dermatologistId the IAM user id of the dermatologist
 */
public record AppointmentCompletedEvent(Long appointmentId, Long patientId, Long dermatologistId) {

    /**
     * Factory method that creates the event from the given {@link Appointment} aggregate.
     *
     * @param appointment the aggregate that raised the event
     * @return a new event carrying the aggregate's current state
     */
    public static AppointmentCompletedEvent from(Appointment appointment) {
        return new AppointmentCompletedEvent(
                appointment.getId(),
                appointment.getPatientId().patientId(),
                appointment.getDermatologistId().dermatologistId());
    }
}
