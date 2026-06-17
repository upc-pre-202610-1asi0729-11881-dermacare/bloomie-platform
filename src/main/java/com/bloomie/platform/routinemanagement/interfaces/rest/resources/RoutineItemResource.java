package com.bloomie.platform.routinemanagement.interfaces.rest.resources;

/**
 * Resource representing a routine item in REST responses.
 *
 * @param id            the unique identifier of the routine item
 * @param routineId     the identifier of the routine this item belongs to
 * @param productId     the identifier of the product used in this step
 * @param step          the skincare step this item corresponds to
 * @param scheduledTime the scheduled time of day for applying this product (e.g. "08:00")
 * @param status        the current application status of this item
 * @param order         the display order of this item within the routine
 */
public record RoutineItemResource(
        Long id,
        Long routineId,
        Long productId,
        String step,
        String scheduledTime,
        String status,
        Integer order
) {
}