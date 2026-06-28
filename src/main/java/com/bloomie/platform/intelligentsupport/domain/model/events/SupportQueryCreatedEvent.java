package com.bloomie.platform.intelligentsupport.domain.model.events;

import com.bloomie.platform.intelligentsupport.domain.model.aggregates.SupportQuery;

import java.time.LocalDateTime;

public record SupportQueryCreatedEvent(
        Long supportQueryId,
        Long patientId,
        Long skinProfileId,
        String status,
        String suggestedAction,
        LocalDateTime createdAt
) {
    public static SupportQueryCreatedEvent from(SupportQuery supportQuery){
        return new SupportQueryCreatedEvent (
                supportQuery.getId(),
                supportQuery.getPatientId().patientId(),
                supportQuery.getSkinProfileId().skinProfileId(),
                supportQuery.getStatus().name(),
                supportQuery.getSuggestedAction().name(),
                supportQuery.getCreatedAt()
        );
    }
}