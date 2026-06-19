package com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.repositories;

import com.bloomie.platform.routinemanagement.domain.model.valueobjects.PatientId;
import com.bloomie.platform.routinemanagement.domain.model.valueobjects.RoutineStatus;
import com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.entities.RoutinePersistenceEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data repository for routine persistence entities.
 */
@Repository
public interface RoutinePersistenceRepository extends JpaRepository<RoutinePersistenceEntity, Long> {

    /**
     * Returns the single routine with the given patient and status, or empty if none.
     *
     * <p>Using a status filter avoids the {@code IncorrectResultSizeDataAccessException}
     * that would occur when a patient has both active and inactive routines.</p>
     */
    Optional<RoutinePersistenceEntity> findByPatientIdAndStatus(PatientId patientId, RoutineStatus status);

    boolean existsByPatientId(PatientId patientId);

    @EntityGraph(value = "Routine.withItems")
    @Override
    Optional<RoutinePersistenceEntity> findById(Long id);
}