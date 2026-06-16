package com.bloomie.platform.dermatologyCare.infrastructure.persistence.jpa.repositories;

import com.bloomie.platform.dermatologyCare.domain.model.valueobjects.DermatologistId;
import com.bloomie.platform.dermatologyCare.infrastructure.persistence.jpa.entities.DermatologistProfilePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DermatologistProfilePersistenceRepository
        extends JpaRepository<DermatologistProfilePersistenceEntity, Long> {

    @Query("select d from DermatologistProfilePersistenceEntity d where d.dermatologistId = :dermatologistId")
    Optional<DermatologistProfilePersistenceEntity> findByDermatologistId(
            @Param("dermatologistId") DermatologistId dermatologistId);

    @Query("select count(d) from DermatologistProfilePersistenceEntity d where d.dermatologistId = :dermatologistId")
    long countByDermatologistId(@Param("dermatologistId") DermatologistId dermatologistId);
}
