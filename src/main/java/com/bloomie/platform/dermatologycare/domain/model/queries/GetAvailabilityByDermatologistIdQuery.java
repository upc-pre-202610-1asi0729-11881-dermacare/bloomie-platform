package com.bloomie.platform.dermatologycare.domain.model.queries;

import com.bloomie.platform.dermatologycare.domain.model.valueobjects.DermatologistId;

/**
 * Query to retrieve all availability slots for a dermatologist.
 *
 * @param dermatologistId the IAM user id wrapped in a {@link DermatologistId} VO
 */
public record GetAvailabilityByDermatologistIdQuery(DermatologistId dermatologistId) {

    private static final String DERMATOLOGIST_ID_BLANK_KEY = "dermatology.id.blank";

    public GetAvailabilityByDermatologistIdQuery {
        if (dermatologistId == null) {
            throw new IllegalArgumentException(DERMATOLOGIST_ID_BLANK_KEY);
        }
    }
}
