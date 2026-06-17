package com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.adapters;

import com.bloomie.platform.routinemanagement.domain.model.entities.RoutineItem;
import com.bloomie.platform.routinemanagement.domain.repositories.RoutineItemRepository;
import com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.assemblers.RoutineItemPersistenceAssembler;
import com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.repositories.RoutineItemPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository adapter that bridges the routine item domain repository port with Spring Data JPA.
 */
@Repository
public class RoutineItemRepositoryImpl implements RoutineItemRepository {

    private final RoutineItemPersistenceRepository routineItemPersistenceRepository;

    public RoutineItemRepositoryImpl(RoutineItemPersistenceRepository routineItemPersistenceRepository) {
        this.routineItemPersistenceRepository = routineItemPersistenceRepository;
    }

    @Override
    public List<RoutineItem> findAll() {
        return routineItemPersistenceRepository.findAll().stream()
                .map(RoutineItemPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }
}