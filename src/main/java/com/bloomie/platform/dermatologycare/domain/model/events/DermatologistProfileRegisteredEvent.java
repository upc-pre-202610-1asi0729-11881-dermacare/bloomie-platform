package com.bloomie.platform.dermatologycare.domain.model.events;

import com.bloomie.platform.dermatologycare.domain.model.aggregates.DermatologistProfile;

public record DermatologistProfileRegisteredEvent(Long dermatologistProfileId, Long dermatologisId, String fullName) {
    public static DermatologistProfileRegisteredEvent from(DermatologistProfile profile) {
        return new DermatologistProfileRegisteredEvent(
                profile.getId(),
                profile.getDermatologistId(),
                profile.getFullName()
        );
    }
}
