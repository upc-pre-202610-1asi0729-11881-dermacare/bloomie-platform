package com.bloomie.platform.dermatologyCare.domain.model.events;

import com.bloomie.platform.dermatologyCare.domain.model.aggregates.DermatologistProfile;

public record DermatologistProfileRegisteredEvent(Long dermatologistProfileId, Long dermatologisId, String fullName) {
    public static DermatologistProfileRegisteredEvent from(DermatologistProfile profile) {
        return new DermatologistProfileRegisteredEvent(
                profile.getId(),
                profile.getDermatologistId(),
                profile.getFullName()
        );
    }
}
