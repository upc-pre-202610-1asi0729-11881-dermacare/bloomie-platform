package com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.adapters;

import com.bloomie.platform.routinemanagement.domain.model.aggregates.Routine;
import com.bloomie.platform.routinemanagement.domain.model.valueobjects.PatientId;
import com.bloomie.platform.routinemanagement.domain.model.valueobjects.RoutineStatus;
import com.bloomie.platform.routinemanagement.domain.repositories.RoutineRepository;
import com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.assemblers.RoutinePersistenceAssembler;
import com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.repositories.RoutinePersistenceRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA adapter for the {@link RoutineRepository} domain port.
 *
 * <p>For <em>new</em> aggregates ({@code id == null}): saves the entity, then calls
 * {@code onGenerated()} on the reconstructed aggregate, publishes events and clears them.</p>
 */
@Repository
public class RoutineRepositoryImpl implements RoutineRepository {

    private final RoutinePersistenceRepository routinePersistenceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public RoutineRepositoryImpl(RoutinePersistenceRepository routinePersistenceRepository,
                                 ApplicationEventPublisher eventPublisher) {
        this.routinePersistenceRepository = routinePersistenceRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<Routine> findById(Long id) {
        return routinePersistenceRepository.findById(id)
                .map(RoutinePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<Routine> findActiveByPatientId(PatientId patientId) {
        return routinePersistenceRepository.findByPatientIdAndStatus(patientId, RoutineStatus.ACTIVE)
                .map(RoutinePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Routine save(Routine routine) {
        boolean isNew = routine.getId() == null;
        var entity = RoutinePersistenceAssembler.toPersistenceFromDomain(routine);
        var savedEntity = routinePersistenceRepository.save(entity);
        var savedRoutine = RoutinePersistenceAssembler.toDomainFromPersistence(savedEntity);
        if (isNew) {
            savedRoutine.onGenerated();
        }
        savedRoutine.domainEvents().forEach(eventPublisher::publishEvent);
        savedRoutine.clearDomainEvents();
        return savedRoutine;
    }

    @Override
    public boolean existsByPatientId(PatientId patientId) {
        return routinePersistenceRepository.existsByPatientId(patientId);
    }
}