package com.bloomie.platform.dermatologicalappointment.domain.repositories;

import com.bloomie.platform.dermatologicalappointment.domain.model.aggregates.Appointment;
import com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects.AppointmentDateTime;
import com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects.DermatologistId;
import com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects.PatientId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository port for the {@link Appointment} aggregate.
 *
 * <p>Defines the persistence contract without any JPA or Spring Data dependency.
 * All methods use domain types — no persistence entities cross this boundary.</p>
 */
public interface AppointmentRepository {

    Optional<Appointment> findById(Long id);

    List<Appointment> findByPatientId(PatientId patientId);

    List<Appointment> findByDermatologistId(DermatologistId dermatologistId);

    /**
     * Persists the appointment and returns the saved instance.
     *
     * <p>The adapter detects {@code id == null} (new aggregate), sets the generated id on the
     * original, calls {@code onScheduled()}, publishes events, clears them, and returns a clean
     * reconstructed domain object. For updates, the adapter publishes already-registered events
     * from the original aggregate and returns a clean reconstruction.</p>
     */
    Appointment save(Appointment appointment);

    boolean existsById(Long id);

    /**
     * Returns {@code true} if a non-cancelled appointment already occupies the given time slot
     * for the given dermatologist (double-booking guard).
     */
    boolean existsByDermatologistIdAndDate(DermatologistId dermatologistId, AppointmentDateTime scheduledAt);
}
