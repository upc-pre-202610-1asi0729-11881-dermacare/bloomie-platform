package com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.entities;

import com.bloomie.platform.routinemanagement.domain.model.valueobjects.RoutineStatus;
import com.bloomie.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity for routines.
 */
@Entity
@Table(name = "routines")
@Getter
@Setter
@NoArgsConstructor
public class RoutinePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "skin_profile_id", nullable = false)
    private Long skinProfileId;

    @Column(name = "facial_scan_id", nullable = false)
    private Long facialScanId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoutineStatus status;
}