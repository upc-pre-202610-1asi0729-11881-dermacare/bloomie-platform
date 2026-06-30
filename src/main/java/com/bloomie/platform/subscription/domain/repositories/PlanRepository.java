package com.bloomie.platform.subscription.domain.repositories;

import com.bloomie.platform.subscription.domain.model.entities.Plan;
import com.bloomie.platform.subscription.domain.model.valueobjects.PlanType;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository port for the {@link Plan} entity.
 *
 * <p>Separates plan persistence from subscription persistence following the
 * single-responsibility principle. The infrastructure layer provides
 * the adapter via {@link com.bloomie.platform.subscription.infrastructure.adapters.PlanRepositoryImpl}.</p>
 */
public interface PlanRepository {

    /** Returns all plans stored in the system. */
    List<Plan> findAll();

    /** Finds a plan by its unique identifier. */
    Optional<Plan> findById(Long id);

    /** Finds a plan by its type. */
    Optional<Plan> findByType(PlanType type);

    /** Persists a new plan or updates an existing one. */
    Plan save(Plan plan);

    /** Returns the total number of plans. */
    long count();
}