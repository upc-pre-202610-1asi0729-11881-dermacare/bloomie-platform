package com.bloomie.platform.routinemanagement.interfaces.rest.resources;

/**
 * Resource for creating a new routine.
 *
 * @param userId        the identifier of the user who owns the routine
 * @param skinProfileId the identifier of the associated skin profile
 * @param facialScanId  the identifier of the facial scan that generated this routine
 * @param status        the initial lifecycle status (ACTIVE, UPDATE, INACTIVE)
 */
public record CreateRoutineResource(Long userId, Long skinProfileId, Long facialScanId, String status) {
}