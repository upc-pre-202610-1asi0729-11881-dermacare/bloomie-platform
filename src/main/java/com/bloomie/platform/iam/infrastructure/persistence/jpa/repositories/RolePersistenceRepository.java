package com.bloomie.platform.iam.infrastructure.persistence.jpa.repositories;

import com.bloomie.platform.iam.domain.model.valueobjects.UserRole;
import com.bloomie.platform.iam.infrastructure.persistence.jpa.entities.RolePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolePersistenceRepository extends JpaRepository<RolePersistenceEntity, Long> {
    Optional<RolePersistenceEntity> findByName(UserRole name);
    boolean existsByName(UserRole name);
}