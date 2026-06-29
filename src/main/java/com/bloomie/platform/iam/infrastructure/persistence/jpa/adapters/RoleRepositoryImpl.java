package com.bloomie.platform.iam.infrastructure.persistence.jpa.adapters;

import com.bloomie.platform.iam.domain.model.entities.Role;
import com.bloomie.platform.iam.domain.model.valueobjects.UserRole;
import com.bloomie.platform.iam.domain.repositories.RoleRepository;
import com.bloomie.platform.iam.infrastructure.persistence.jpa.assemblers.RolePersistenceAssembler;
import com.bloomie.platform.iam.infrastructure.persistence.jpa.entities.RolePersistenceEntity;
import com.bloomie.platform.iam.infrastructure.persistence.jpa.repositories.RolePersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter that implements the domain {@link RoleRepository} port using JPA.
 *
 * <p>Translates between the domain {@link Role} entity and its JPA counterpart
 * {@link RolePersistenceEntity} via the {@link RolePersistenceAssembler}.</p>
 */
@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private final RolePersistenceRepository rolePersistenceRepository;

    public RoleRepositoryImpl(RolePersistenceRepository rolePersistenceRepository) {
        this.rolePersistenceRepository = rolePersistenceRepository;
    }

    @Override
    public Optional<Role> findByName(UserRole name) {
        return rolePersistenceRepository.findByName(name)
                .map(RolePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Role> findAll() {
        return rolePersistenceRepository.findAll().stream()
                .map(RolePersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Role save(Role role) {
        var entity = RolePersistenceAssembler.toPersistenceFromDomain(role);
        return RolePersistenceAssembler.toDomainFromPersistence(rolePersistenceRepository.save(entity));
    }

    @Override
    public boolean existsByName(UserRole name) {
        return rolePersistenceRepository.existsByName(name);
    }
}
