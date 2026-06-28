package com.bloomie.platform.intelligentsupport.infrastructure.persistence.jpa.assemblers;

import com.bloomie.platform.intelligentsupport.application.commandservices.SupportQueryCommandService;
import com.bloomie.platform.intelligentsupport.domain.model.aggregates.SupportQuery;
import com.bloomie.platform.intelligentsupport.infrastructure.persistence.jpa.entities.SupportQueryPersistenceEntity;

import java.time.ZoneId;

public final class SupportQueryPersistenceAssembler {
    private SupportQueryPersistenceAssembler() {
    }

    public static SupportQuery toDomainFromPersistence(SupportQueryPersistenceEntity entity) {
        return new SupportQuery(
                entity.getId(),
                entity.getPatientId(),
                entity.getSkinProfileId(),
                entity.getStatus(),
                entity.getSuggestedAction(),
                entity.getCreatedAt()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime()
        );
    }

    public static SupportQueryPersistenceEntity toPersistenceFromDomain(SupportQuery supportQuery) {
        var entity = new SupportQueryPersistenceEntity();
        entity.setId(supportQuery.getId());
        entity.setPatientId(supportQuery.getPatientId());
        entity.setSkinProfileId(supportQuery.getSkinProfileId());
        entity.setStatus(supportQuery.getStatus());
        entity.setSuggestedAction(supportQuery.getSuggestedAction());
        return entity;
    }
}
