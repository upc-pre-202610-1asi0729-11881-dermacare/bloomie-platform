package com.bloomie.platform.dermatologicalappointment.application.queryservices;

import com.bloomie.platform.dermatologicalappointment.domain.model.aggregates.Appointment;
import com.bloomie.platform.dermatologicalappointment.domain.model.queries.GetAppointmentByIdQuery;
import com.bloomie.platform.dermatologicalappointment.domain.model.queries.GetAppointmentsByDermatologistIdQuery;
import com.bloomie.platform.dermatologicalappointment.domain.model.queries.GetAppointmentsByPatientIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service interface for all read operations on the {@link Appointment} aggregate.
 *
 * <p>This interface is the public contract of the query side.</p>
 */
public interface AppointmentQueryService {

    /**
     * Returns the appointment with the given id, or an empty optional if not found.
     *
     * @param query the query carrying the appointment id
     * @return an optional containing the appointment
     */
    Optional<Appointment> handle(GetAppointmentByIdQuery query);

    /**
     * Returns all appointments belonging to the given patient.
     *
     * @param query the query carrying the patient's IAM id
     * @return a list of matching appointments; never {@code null}
     */
    List<Appointment> handle(GetAppointmentsByPatientIdQuery query);

    /**
     * Returns all appointments assigned to the given dermatologist.
     *
     * @param query the query carrying the dermatologist's IAM id
     * @return a list of matching appointments; never {@code null}
     */
    List<Appointment> handle(GetAppointmentsByDermatologistIdQuery query);
}
