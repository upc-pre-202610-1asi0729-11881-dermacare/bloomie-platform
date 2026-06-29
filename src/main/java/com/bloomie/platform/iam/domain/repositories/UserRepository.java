package com.bloomie.platform.iam.domain.repositories;

import com.bloomie.platform.iam.domain.model.aggregates.User;
import com.bloomie.platform.iam.domain.model.valueobjects.EmailAddress;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository port for the {@link User} aggregate.
 *
 * <p>Defines the persistence contract without any JPA or Spring Data dependency.
 * The infrastructure layer provides the concrete implementation via an adapter.
 * Role lookups are handled by {@link RoleRepository} to respect single responsibility.</p>
 */
public interface UserRepository {
    Optional<User> findById(Long id);
    Optional<User> findByEmailAddress(EmailAddress emailAddress);
    List<User> findAll();
    User save(User user);
    boolean existsByEmailAddress(EmailAddress emailAddress);
}
