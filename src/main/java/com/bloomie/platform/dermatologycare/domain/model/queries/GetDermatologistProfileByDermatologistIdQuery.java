package com.bloomie.platform.dermatologycare.domain.model.queries;

import com.bloomie.platform.dermatologycare.domain.model.valueobjects.DermatologistId;

/**
 * Query to retrieve a {@code DermatologistProfile} by the dermatologist's IAM user id.
 *
 * @param dermatologistId the IAM user id wrapped in a {@link DermatologistId} VO
 */
public record GetDermatologistProfileByDermatologistIdQuery(DermatologistId dermatologistId) {

    private static final String DERMATOLOGIST_ID_BLANK_KEY = "dermatology.id.blank";

    public GetDermatologistProfileByDermatologistIdQuery {
        if (dermatologistId == null) {
            throw new IllegalArgumentException(DERMATOLOGIST_ID_BLANK_KEY);
        }
    }
}
