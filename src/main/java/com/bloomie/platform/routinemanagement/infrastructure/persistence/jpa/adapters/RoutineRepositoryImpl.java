package com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.adapters;

import com.bloomie.platform.routinemanagement.domain.model.aggregates.Routine;
import com.bloomie.platform.routinemanagement.domain.repositories.RoutineRepository;
import com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.assemblers.RoutinePersistenceAssembler;
import com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.repositories.RoutinePersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository adapter that bridges the routine domain repository port with Spring Data JPA.
 */
@Repository
public class RoutineRepositoryImpl implements RoutineRepository {

    private final RoutinePersistenceRepository routinePersistenceRepository;

    public RoutineRepositoryImpl(RoutinePersistenceRepository routinePersistenceRepository) {
        this.routinePersistenceRepository = routinePersistenceRepository;
    }

    @Override
    public List<Routine> findAll() {
        return routinePersistenceRepository.findAll().stream()
                .map(RoutinePersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Optional<Routine> findById(Long id) {
        return routinePersistenceRepository.findById(id)
                .map(RoutinePersistenceAssembler::toDomainFromPersistence);
    }
}