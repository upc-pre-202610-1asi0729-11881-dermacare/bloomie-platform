package com.bloomie.platform.dermatologicalappointment.infrastructure.persistence.jpa.repositories;

import com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects.AppointmentDateTime;
import com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects.AppointmentStatus;
import com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects.DermatologistId;
import com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects.PatientId;
import com.bloomie.platform.dermatologicalappointment.infrastructure.persistence.jpa.entities.AppointmentPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentPersistenceRepository extends JpaRepository<AppointmentPersistenceEntity, Long> {

    List<AppointmentPersistenceEntity> findAllByPatientId(PatientId patientId);

    List<AppointmentPersistenceEntity> findAllByDermatologistId(DermatologistId dermatologistId);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
           "FROM AppointmentPersistenceEntity a " +
           "WHERE a.dermatologistId = :dermatologistId " +
           "AND a.scheduledAt = :scheduledAt " +
           "AND a.status <> :cancelled")
    boolean existsByDermatologistIdAndScheduledAtAndStatusNot(
            @Param("dermatologistId") DermatologistId dermatologistId,
            @Param("scheduledAt") AppointmentDateTime scheduledAt,
            @Param("cancelled") AppointmentStatus cancelled);
}
