package com.bloomie.platform.routinemanagement.domain.model.entities;

import lombok.Getter;

/**
 * Entity representing a single product step within a personalized skincare routine.
 * This is not an aggregate — it lives inside the Routine aggregate boundary.
 */
@Getter
public class RoutineItem {

    private Long id;
    private String step;
    private Integer order;
    private String scheduledTime;
    private String productRecommendation;

    public RoutineItem(Long id, String step, Integer order, String scheduledTime, String productRecommendation) {
        this.id = id;
        this.step = step;
        this.order = order;
        this.scheduledTime = scheduledTime;
        this.productRecommendation = productRecommendation;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void updateProductRecommendation(String newProductRecommendation) {
        this.productRecommendation = newProductRecommendation;
    }
}