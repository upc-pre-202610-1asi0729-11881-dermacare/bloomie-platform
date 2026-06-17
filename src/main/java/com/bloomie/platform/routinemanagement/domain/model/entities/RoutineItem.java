package com.bloomie.platform.routinemanagement.domain.model.entities;

import com.bloomie.platform.routinemanagement.domain.model.valueobjects.RoutineItemStatus;
import com.bloomie.platform.routinemanagement.domain.model.valueobjects.RoutineStep;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a single product step within a skincare routine.
 */
@Getter
public class RoutineItem {

    @Setter
    private Long id;

    @Setter
    private Long routineId;

    @Setter
    private Long productId;

    @Setter
    private RoutineStep step;

    @Setter
    private String scheduledTime;

    @Setter
    private RoutineItemStatus status;

    @Setter
    private Integer order;

    public RoutineItem() {
    }
}