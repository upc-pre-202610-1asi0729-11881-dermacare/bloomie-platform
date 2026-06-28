package com.bloomie.platform.intelligentsupport.domain.model.events;

import com.bloomie.platform.intelligentsupport.domain.model.aggregates.SupportQuery;

/**
 * Domain event raised when the status of a support query has been updated.
 *
 * @param supportQueryId the id of the updated support query
 * @param patientId      the IAM user id of the patient
 * @param status         the new lifecycle status
 */
public record SupportQueryStatusUpdatedEvent(
        Long supportQueryId,
        Long patientId,
        String status
) {
    public static SupportQueryStatusUpdatedEvent from(SupportQuery supportQuery) {
        return new SupportQueryStatusUpdatedEvent(
                supportQuery.getId(),
                supportQuery.getPatientId().patientId(),
                supportQuery.getStatus().name()
        );
    }
}