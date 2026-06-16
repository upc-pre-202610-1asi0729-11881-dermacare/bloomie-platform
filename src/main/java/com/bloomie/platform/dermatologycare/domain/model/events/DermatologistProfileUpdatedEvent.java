package com.bloomie.platform.dermatologyCare.domain.model.events;

import com.bloomie.platform.dermatologyCare.domain.model.aggregates.DermatologistProfile;

/**
 * Domain event raised when a {@link DermatologistProfile} has been updated.
 *
 * @param dermatologistProfileId the profile's own persistence id
 * @param dermatologistId        the IAM user id of the dermatologist
 * @param fullName               the dermatologist's full name after the update
 */
public record DermatologistProfileUpdatedEvent(Long dermatologistProfileId, Long dermatologistId, String fullName) {

    public static DermatologistProfileUpdatedEvent from(DermatologistProfile profile) {
        return new DermatologistProfileUpdatedEvent(
                profile.getId(),
                profile.getDermatologistId(),
                profile.getFullName());
    }
}
