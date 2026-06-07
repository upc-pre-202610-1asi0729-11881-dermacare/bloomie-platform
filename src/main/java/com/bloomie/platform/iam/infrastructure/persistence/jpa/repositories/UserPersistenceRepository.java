package com.bloomie.platform.iam.infrastructure.persistence.jpa.repositories;

import com.bloomie.platform.iam.domain.model.valueobjects.EmailAddress;
import com.bloomie.platform.iam.infrastructure.persistence.jpa.entities.UserPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPersistenceRepository extends JpaRepository<UserPersistenceEntity, Long> {

    @Query("select u from UserPersistenceEntity u where u.emailAddress = :emailAddress")
    Optional<UserPersistenceEntity> findByEmailAddress(@Param("emailAddress") EmailAddress emailAddress);

    @Query("select count(u) from UserPersistenceEntity u where u.emailAddress = :emailAddress")
    long countByEmailAddress(@Param("emailAddress") EmailAddress emailAddress);
}