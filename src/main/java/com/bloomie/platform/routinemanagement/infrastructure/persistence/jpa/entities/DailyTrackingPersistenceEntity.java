package com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.entities;

import com.bloomie.platform.routinemanagement.domain.model.valueobjects.TrackingStatus;
import com.bloomie.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * JPA persistence entity for daily tracking entries.
 */
@Entity
@Table(name = "daily_trackings")
@Getter
@Setter
@NoArgsConstructor
public class DailyTrackingPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "routine_id", nullable = false)
    private Long routineId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "tracking_date", nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrackingStatus status;
}