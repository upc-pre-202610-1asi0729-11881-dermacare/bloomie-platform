package com.bloomie.platform.routinemanagement.interfaces.rest.resources;

import java.util.List;

/**
 * Resource representing a routine in REST responses.
 *
 * @param id             the unique identifier of the routine
 * @param patientId      the identifier of the patient who owns the routine
 * @param skinAnalysisId the identifier of the skin analysis that generated this routine
 * @param status         the current lifecycle status of the routine
 * @param createdAt      the ISO 8601 date-time string for when the routine was created
 * @param items          the list of routine items (steps)
 */
public record RoutineResource(
        Long id,
        Long patientId,
        Long skinAnalysisId,
        String status,
        String createdAt,
        List<RoutineItemResource> items
) {
}