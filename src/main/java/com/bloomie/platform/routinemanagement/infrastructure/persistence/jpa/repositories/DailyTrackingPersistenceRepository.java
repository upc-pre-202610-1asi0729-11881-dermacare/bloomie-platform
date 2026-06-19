package com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.repositories;

import com.bloomie.platform.routinemanagement.domain.model.valueobjects.PatientId;
import com.bloomie.platform.routinemanagement.domain.model.valueobjects.RoutineId;
import com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.entities.DailyTrackingPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for daily tracking persistence entities.
 */
@Repository
public interface DailyTrackingPersistenceRepository extends JpaRepository<DailyTrackingPersistenceEntity, Long> {

    /**
     * Finds the tracking entry for a patient on a specific date.
     *
     * @param patientId the patient value object
     * @param date      the tracking date
     * @return an {@link Optional} with the entity if found
     */
    Optional<DailyTrackingPersistenceEntity> findByPatientIdAndDate(PatientId patientId, LocalDate date);

    /**
     * Finds all tracking entries belonging to a patient.
     *
     * @param patientId the patient value object
     * @return list of tracking entities for the patient
     */
    List<DailyTrackingPersistenceEntity> findAllByPatientId(PatientId patientId);

    /**
     * Finds all tracking entries associated with a routine.
     *
     * @param routineId the routine value object
     * @return list of tracking entities for the routine
     */
    List<DailyTrackingPersistenceEntity> findAllByRoutineId(RoutineId routineId);

    /**
     * Checks whether a tracking entry already exists for the given patient on the given date.
     *
     * @param patientId the patient value object
     * @param date      the tracking date
     * @return {@code true} if an entry exists
     */
    boolean existsByPatientIdAndDate(PatientId patientId, LocalDate date);
}
