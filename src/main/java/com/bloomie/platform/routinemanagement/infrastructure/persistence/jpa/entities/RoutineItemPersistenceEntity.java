package com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.entities;

import com.bloomie.platform.routinemanagement.domain.model.valueobjects.RoutineItemStatus;
import com.bloomie.platform.routinemanagement.domain.model.valueobjects.RoutineStep;
import com.bloomie.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity for routine items.
 */
@Entity
@Table(name = "routine_items")
@Getter
@Setter
@NoArgsConstructor
public class RoutineItemPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "routine_id", nullable = false)
    private Long routineId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoutineStep step;

    @Column(name = "scheduled_time", nullable = false)
    private String scheduledTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoutineItemStatus status;

    @Column(name = "`order`", nullable = false)
    private Integer order;
}