package com.bloomie.platform.dermatologicalappointment.application.queryservices;

import com.bloomie.platform.dermatologicalappointment.domain.model.aggregates.Consultation;
import com.bloomie.platform.dermatologicalappointment.domain.model.queries.GetConsultationByAppointmentIdQuery;
import com.bloomie.platform.dermatologicalappointment.domain.model.queries.GetConsultationByIdQuery;

import java.util.Optional;

/**
 * Application service interface for all read operations on the {@link Consultation} aggregate.
 *
 * <p>This interface is the public contract of the query side.</p>
 */
public interface ConsultationQueryService {

    /**
     * Returns the consultation with the given id, or an empty optional if not found.
     *
     * @param query the query carrying the consultation id
     * @return an optional containing the consultation
     */
    Optional<Consultation> handle(GetConsultationByIdQuery query);

    /**
     * Returns the consultation associated with the given appointment, or an empty optional
     * if no consultation has been started yet.
     *
     * @param query the query carrying the appointment id
     * @return an optional containing the consultation
     */
    Optional<Consultation> handle(GetConsultationByAppointmentIdQuery query);
}
