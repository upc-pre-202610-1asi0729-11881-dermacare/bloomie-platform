package com.bloomie.platform.iam.infrastructure.persistence.jpa.adapters;

import com.bloomie.platform.iam.domain.model.aggregates.User;
import com.bloomie.platform.iam.domain.model.entities.Role;
import com.bloomie.platform.iam.domain.model.valueobjects.EmailAddress;
import com.bloomie.platform.iam.domain.model.valueobjects.UserRole;
import com.bloomie.platform.iam.domain.repositories.UserRepository;
import com.bloomie.platform.iam.infrastructure.persistence.jpa.assemblers.RolePersistenceAssembler;
import com.bloomie.platform.iam.infrastructure.persistence.jpa.assemblers.UserPersistenceAssembler;
import com.bloomie.platform.iam.infrastructure.persistence.jpa.repositories.RolePersistenceRepository;
import com.bloomie.platform.iam.infrastructure.persistence.jpa.repositories.UserPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter that implements the domain {@link UserRepository} port using JPA.
 *
 * <p>Translates between the domain {@link User} aggregate / {@link Role} entity
 * and their JPA counterparts via the persistence assemblers. All persistence
 * concerns are confined to this class and the infrastructure package.</p>
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserPersistenceRepository userPersistenceRepository;
    private final RolePersistenceRepository rolePersistenceRepository;

    public UserRepositoryImpl(UserPersistenceRepository userPersistenceRepository,
                              RolePersistenceRepository rolePersistenceRepository) {
        this.userPersistenceRepository = userPersistenceRepository;
        this.rolePersistenceRepository = rolePersistenceRepository;
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
        var entity = UserPersistenceAssembler.toPersistenceFromDomain(user);
        var savedEntity = userPersistenceRepository.save(entity);
        return UserPersistenceAssembler.toDomainFromPersistence(savedEntity);
    }

    @Override
    public boolean existsByEmailAddress(EmailAddress emailAddress) {
        return userPersistenceRepository.countByEmailAddress(emailAddress) > 0;
    }

    // Looks up the role by enum name and converts it to a domain Role.
    // Used by the command service when registering a user to obtain the pre-seeded role entity.
    @Override
    public Optional<Role> findRoleByName(UserRole name) {
        return rolePersistenceRepository.findByName(name)
                .map(RolePersistenceAssembler::toDomainFromPersistence);
    }
}