// AvailabilityPersistenceRepository.java
package com.bloomie.platform.dermatologyCare.infrastructure.persistence.jpa.repositories;

import com.bloomie.platform.dermatologyCare.domain.model.valueobjects.DermatologistId;
import com.bloomie.platform.dermatologyCare.infrastructure.persistence.jpa.entities.AvailabilityPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface AvailabilityPersistenceRepository extends JpaRepository<AvailabilityPersistenceEntity, Long> {

    @Query("select a from AvailabilityPersistenceEntity a where a.dermatologistId = :dermatologistId")
    List<AvailabilityPersistenceEntity> findAllByDermatologistId(@Param("dermatologistId") DermatologistId dermatologistId);

    @Query("select a from AvailabilityPersistenceEntity a where a.dermatologistId = :dermatologistId and a.day = :day")
    List<AvailabilityPersistenceEntity> findAllByDermatologistIdAndDay(
            @Param("dermatologistId") DermatologistId dermatologistId,
            @Param("day") DayOfWeek day);

    @Query("select count(a) from AvailabilityPersistenceEntity a where a.dermatologistId = :dermatologistId and a.day = :day")
    long countByDermatologistIdAndDay(
            @Param("dermatologistId") DermatologistId dermatologistId,
            @Param("day") DayOfWeek day);
}