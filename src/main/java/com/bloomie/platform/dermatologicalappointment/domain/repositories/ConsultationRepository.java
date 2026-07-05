package com.bloomie.platform.dermatologicalappointment.domain.repositories;

import com.bloomie.platform.dermatologicalappointment.domain.model.aggregates.Consultation;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository port for the {@link Consultation} aggregate.
 *
 * <p>Defines the persistence contract without any JPA or Spring Data dependency.
 * The infrastructure layer provides the concrete implementation via an adapter.</p>
 */
public interface ConsultationRepository {

    /**
     * Returns the consultation with the given id, or an empty optional if not found.
     *
     * @param id the persistence id to look up
     * @return an optional containing the consultation, or empty
     */
    Optional<Consultation> findById(Long id);

    /**
     * Returns the consultation associated with the given appointment id, or an empty optional
     * if no consultation has been started for that appointment.
     *
     * @param appointmentId the id of the owning appointment
     * @return an optional containing the consultation, or empty
     */
    Optional<Consultation> findByAppointmentId(Long appointmentId);

    /**
     * Persists the given consultation and returns the saved instance (with id populated
     * if it was a new aggregate).
     *
     * <p>The adapter is responsible for detecting {@code id == null} (new aggregate),
     * calling {@code consultation.onStarted()}, publishing domain events via
     * {@code ApplicationEventPublisher}, and clearing them with {@code clearDomainEvents()}.</p>
     *
     * @param consultation the aggregate to persist
     * @return the persisted aggregate
     */
    Consultation save(Consultation consultation);

    List<Consultation> findAll();
}
