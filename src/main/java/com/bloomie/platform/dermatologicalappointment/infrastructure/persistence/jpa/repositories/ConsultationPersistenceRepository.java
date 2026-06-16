package com.bloomie.platform.dermatologicalAppointment.infrastructure.persistence.jpa.repositories;

import com.bloomie.platform.dermatologicalAppointment.infrastructure.persistence.jpa.entities.ConsultationPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConsultationPersistenceRepository extends JpaRepository<ConsultationPersistenceEntity, Long> {

    Optional<ConsultationPersistenceEntity> findByAppointmentId(Long appointmentId);
}
