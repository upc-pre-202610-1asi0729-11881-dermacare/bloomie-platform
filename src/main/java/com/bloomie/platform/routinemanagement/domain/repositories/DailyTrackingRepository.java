package com.bloomie.platform.routinemanagement.domain.repositories;

import com.bloomie.platform.routinemanagement.domain.model.aggregates.DailyTracking;
import com.bloomie.platform.routinemanagement.domain.model.valueobjects.PatientId;
import com.bloomie.platform.routinemanagement.domain.model.valueobjects.RoutineId;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository port for the {@link DailyTracking} aggregate.
 */
public interface DailyTrackingRepository {

    /**
     * Retrieves a daily tracking entry by its identifier.
     *
     * @param id the tracking entry identifier
     * @return an {@link Optional} containing the tracking entry, or empty if not found
     */
    Optional<DailyTracking> findById(Long id);

    /**
     * Retrieves the daily tracking entry for a given patient on a specific date.
     *
     * @param patientId the patient value object
     * @param date      the date to look up
     * @return an {@link Optional} containing the tracking entry, or empty if not found
     */
    Optional<DailyTracking> findByPatientIdAndDate(PatientId patientId, LocalDate date);

    /**
     * Retrieves all daily tracking entries belonging to a patient.
     *
     * @param patientId the patient value object
     * @return list of daily tracking entries for the given patient
     */
    List<DailyTracking> findAllByPatientId(PatientId patientId);

    /**
     * Retrieves all daily tracking entries associated with a routine.
     *
     * @param routineId the routine value object
     * @return list of daily tracking entries for the given routine
     */
    List<DailyTracking> findAllByRoutineId(RoutineId routineId);

    /**
     * Retrieves all daily tracking entries.
     *
     * @return list of all daily tracking entries
     */
    List<DailyTracking> findAll();

    /**
     * Persists a daily tracking entry and publishes domain events if the entry is new.
     *
     * @param dailyTracking the tracking aggregate to persist
     * @return the saved tracking aggregate with its assigned identifier
     */
    DailyTracking save(DailyTracking dailyTracking);

    /**
     * Checks whether a tracking entry already exists for the given patient on the given date.
     *
     * @param patientId the patient value object
     * @param date      the date to check
     * @return {@code true} if an entry already exists, {@code false} otherwise
     */
    boolean existsByPatientIdAndDate(PatientId patientId, LocalDate date);
}
