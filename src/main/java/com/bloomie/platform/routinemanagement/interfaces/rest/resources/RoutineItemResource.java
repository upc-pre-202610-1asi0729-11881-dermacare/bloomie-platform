package com.bloomie.platform.routinemanagement.interfaces.rest.resources;

/**
 * Resource representing a single routine item in REST responses.
 *
 * @param id                    the unique identifier of the item
 * @param step                  the skincare step (e.g. CLEANSER, TONER, MOISTURIZER)
 * @param order                 the application order within the routine
 * @param scheduledTime         when to apply (AM, PM, AM_PM)
 * @param productRecommendation the recommended product for this step
 */
public record RoutineItemResource(
        Long id,
        String step,
        Integer order,
        String scheduledTime,
        String productRecommendation
) {
}