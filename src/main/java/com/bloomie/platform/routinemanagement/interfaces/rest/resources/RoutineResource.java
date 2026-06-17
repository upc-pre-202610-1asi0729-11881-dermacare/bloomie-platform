package com.bloomie.platform.routinemanagement.interfaces.rest.resources;

/**
 * Resource representing a routine in REST responses.
 *
 * @param id            the unique identifier of the routine
 * @param userId        the identifier of the user who owns the routine
 * @param skinProfileId the identifier of the associated skin profile
 * @param facialScanId  the identifier of the associated facial scan
 * @param status        the current lifecycle status of the routine
 * @param createdAt     the ISO 8601 date-time string for when the routine was created
 */
public record RoutineResource(
        Long id,
        Long userId,
        Long skinProfileId,
        Long facialScanId,
        String status,
        String createdAt
) {
}