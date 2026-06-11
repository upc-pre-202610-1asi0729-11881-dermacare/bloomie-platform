package com.bloomie.platform.dermatologyCare.domain.model.queries;

import com.bloomie.platform.dermatologyCare.domain.model.valueobjects.DermatologistId;

import java.time.DayOfWeek;

/**
 * Query to retrieve availability slots for a dermatologist on a specific day.
 *
 * @param dermatologistId the IAM user id wrapped in a {@link DermatologistId} VO
 * @param day             the day of the week to filter by
 */
public record GetAvailabilityByDermatologistIdAndDayQuery(DermatologistId dermatologistId, DayOfWeek day) {

    private static final String DERMATOLOGIST_ID_BLANK_KEY = "dermatology.id.blank";
    private static final String DAY_NULL_KEY = "dermatology.day.null";

    public GetAvailabilityByDermatologistIdAndDayQuery {
        if (dermatologistId == null) {
            throw new IllegalArgumentException(DERMATOLOGIST_ID_BLANK_KEY);
        }
        if (day == null) {
            throw new IllegalArgumentException(DAY_NULL_KEY);
        }
    }
}
