package com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.entities;

import com.bloomie.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity for routine items owned by a {@link RoutinePersistenceEntity}.
 */
@Entity
@Table(name = "routine_items")
@Getter
@Setter
@NoArgsConstructor
public class RoutineItemPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_id", nullable = false)
    private RoutinePersistenceEntity routine;

    @Column(nullable = false)
    private String step;

    @Column(name = "item_order", nullable = false)
    private Integer order;

    @Column(name = "scheduled_time", nullable = false)
    private String scheduledTime;

    @Column(name = "product_recommendation", nullable = false)
    private String productRecommendation;
}