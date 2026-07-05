package com.bloomie.platform.intelligentsupport.interfaces.rest.transform;

import com.bloomie.platform.intelligentsupport.domain.model.aggregates.SupportQuery;
import com.bloomie.platform.intelligentsupport.interfaces.rest.resources.SupportQueryResource;

/**
 * Converts a {@link SupportQuery} aggregate into a {@link SupportQueryResource} response.
 */
public final class SupportQueryResourceFromEntityAssembler {

    private SupportQueryResourceFromEntityAssembler() {}

    public static SupportQueryResource toResourceFromEntity(SupportQuery supportQuery) {
        return new SupportQueryResource(
                supportQuery.getId(),
                supportQuery.getPatientId().patientId(),
                supportQuery.getSkinProfileId().skinProfileId(),
                supportQuery.getStatus().name(),
                supportQuery.getSuggestedAction().name(),
                supportQuery.getCreatedAt().toString()
        );
    }
}