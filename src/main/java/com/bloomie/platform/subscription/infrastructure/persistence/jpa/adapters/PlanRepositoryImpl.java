package com.bloomie.platform.subscription.infrastructure.persistence.jpa.adapters;

import com.bloomie.platform.subscription.domain.model.entities.Plan;
import com.bloomie.platform.subscription.domain.model.valueobjects.PlanType;
import com.bloomie.platform.subscription.domain.repositories.PlanRepository;
import com.bloomie.platform.subscription.infrastructure.persistence.jpa.assemblers.PlanPersistenceAssembler;
import com.bloomie.platform.subscription.infrastructure.persistence.jpa.repositories.PlanPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter that implements the domain {@link PlanRepository} port using JPA.
 *
 * <p>Translates between the domain {@link Plan} entity and its JPA counterpart
 * via the {@link PlanPersistenceAssembler}.</p>
 */
@Repository
public class PlanRepositoryImpl implements PlanRepository {

    private final PlanPersistenceRepository planPersistenceRepository;

    public PlanRepositoryImpl(PlanPersistenceRepository planPersistenceRepository) {
        this.planPersistenceRepository = planPersistenceRepository;
    }

    @Override
    public List<Plan> findAll() {
        return planPersistenceRepository.findAll().stream()
                .map(PlanPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Optional<Plan> findById(Long id) {
        return planPersistenceRepository.findById(id)
                .map(PlanPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<Plan> findByType(PlanType type) {
        return planPersistenceRepository.findByType(type)
                .map(PlanPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Plan save(Plan plan) {
        var entity = PlanPersistenceAssembler.toPersistenceFromDomain(plan);
        var saved  = planPersistenceRepository.save(entity);
        return PlanPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public long count() {
        return planPersistenceRepository.count();
    }
}