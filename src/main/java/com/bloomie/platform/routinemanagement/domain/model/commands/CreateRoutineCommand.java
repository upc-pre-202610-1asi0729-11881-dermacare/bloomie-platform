package com.bloomie.platform.routinemanagement.domain.model.commands;

import com.bloomie.platform.routinemanagement.domain.model.valueobjects.RoutineStatus;

/**
 * Command to create a new personalized skincare routine.
 *
 * @param userId        the identifier of the user who owns the routine
 * @param skinProfileId the identifier of the associated skin profile
 * @param facialScanId  the identifier of the facial scan that generated this routine
 * @param status        the initial lifecycle status of the routine
 */
public record CreateRoutineCommand(Long userId, Long skinProfileId, Long facialScanId, RoutineStatus status) {
}