package com.bloomie.platform.iam.infrastructure.persistence.jpa.adapters;

import com.bloomie.platform.iam.domain.model.aggregates.User;
import com.bloomie.platform.iam.domain.model.valueobjects.EmailAddress;
import com.bloomie.platform.iam.domain.repositories.UserRepository;
import com.bloomie.platform.iam.infrastructure.persistence.jpa.assemblers.UserPersistenceAssembler;
import com.bloomie.platform.iam.infrastructure.persistence.jpa.repositories.UserPersistenceRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter that implements the domain {@link UserRepository} port using JPA.
 *
 * <p>Translates between the domain {@link User} aggregate and its JPA counterpart
 * via the persistence assembler. For new users, domain events are published via
 * {@link ApplicationEventPublisher} after the aggregate is persisted and its id is known.
 * Role-specific persistence is delegated to {@link com.bloomie.platform.iam.infrastructure.persistence.jpa.adapters.RoleRepositoryImpl}.</p>
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserPersistenceRepository userPersistenceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public UserRepositoryImpl(
            UserPersistenceRepository userPersistenceRepository,
            ApplicationEventPublisher eventPublisher) {
        this.userPersistenceRepository = userPersistenceRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userPersistenceRepository.findById(id)
                .map(UserPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<User> findByEmailAddress(EmailAddress emailAddress) {
        return userPersistenceRepository.findByEmailAddress(emailAddress)
                .map(UserPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<User> findAll() {
        return userPersistenceRepository.findAll().stream()
                .map(UserPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public User save(User user) {
        boolean isNew = user.getId() == null;
        var entity = UserPersistenceAssembler.toPersistenceFromDomain(user);
        var savedEntity = userPersistenceRepository.save(entity);
        var savedUser = UserPersistenceAssembler.toDomainFromPersistence(savedEntity);
        // Publish domain events only for newly created users, after the id is assigned
        if (isNew) {
            savedUser.onRegistered();
            savedUser.domainEvents().forEach(eventPublisher::publishEvent);
            savedUser.clearDomainEvents();
        }
        return savedUser;
    }

    @Override
    public boolean existsByEmailAddress(EmailAddress emailAddress) {
        return userPersistenceRepository.countByEmailAddress(emailAddress) > 0;
    }
}
