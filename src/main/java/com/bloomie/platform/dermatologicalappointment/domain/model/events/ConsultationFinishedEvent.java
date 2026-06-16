package com.bloomie.platform.dermatologicalAppointment.domain.model.events;

import com.bloomie.platform.dermatologicalAppointment.domain.model.aggregates.Consultation;

/**
 * Domain event raised when the dermatologist has finished and closed the consultation session.
 *
 * <p>Consumed by {@code ConsultationFinishedEventHandler}, which dispatches
 * a {@code CompleteAppointmentCommand} to transition the appointment to {@code COMPLETED}.</p>
 *
 * @param consultationId  the id of the finished consultation
 * @param appointmentId   the id of the associated appointment
 * @param dermatologistId the IAM user id of the dermatologist who closed the session
 * @param finishedAt      ISO-8601 string of the session end timestamp
 */
public record ConsultationFinishedEvent(
        Long consultationId,
        Long appointmentId,
        Long dermatologistId,
        String finishedAt) {

    public static ConsultationFinishedEvent from(Consultation consultation) {
        return new ConsultationFinishedEvent(
                consultation.getId(),
                consultation.getAppointmentId(),
                consultation.getDermatologistId().dermatologistId(),
                consultation.getFinishedAt());
    }
}
