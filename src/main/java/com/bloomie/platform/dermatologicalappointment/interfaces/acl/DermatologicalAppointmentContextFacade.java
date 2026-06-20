package com.bloomie.platform.dermatologicalappointment.interfaces.acl;

/**
 * Anti-Corruption Layer facade exposed by the Dermatological Appointment bounded context.
 *
 * <p>Other bounded contexts use this interface to query appointment data without coupling
 * to internal domain types. Only primitive types cross this boundary.</p>
 */
public interface DermatologicalAppointmentContextFacade {

    /**
     * Returns {@code true} if any appointment exists for the given patient id.
     *
     * @param patientId the IAM user id of the patient
     */
    boolean existsAppointmentByPatientId(Long patientId);

    /**
     * Returns the status name of the appointment with the given id, or an empty string if not found.
     *
     * @param appointmentId the persistence id of the appointment
     */
    String fetchAppointmentStatusById(Long appointmentId);
}
