package com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.entities;

import com.bloomie.platform.routinemanagement.domain.model.valueobjects.PatientId;
import com.bloomie.platform.routinemanagement.domain.model.valueobjects.RoutineId;
import com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.converters.PatientIdPersistenceConverter;
import com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.converters.RoutineIdPersistenceConverter;
import com.bloomie.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * JPA persistence entity for daily tracking entries.
 */
@Entity
@Table(name = "daily_trackings")
@Getter
@Setter
@NoArgsConstructor
public class DailyTrackingPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = PatientIdPersistenceConverter.class)
    @Column(name = "patient_id", nullable = false)
    private PatientId patientId;

    @Convert(converter = RoutineIdPersistenceConverter.class)
    @Column(name = "routine_id", nullable = false)
    private RoutineId routineId;

    @Column(name = "tracking_date", nullable = false)
    private LocalDate date;

    @Column(name = "is_completed", nullable = false)
    private boolean completed;

    @Column(name = "completed_at", nullable = false)
    private LocalDateTime completedAt;
}
