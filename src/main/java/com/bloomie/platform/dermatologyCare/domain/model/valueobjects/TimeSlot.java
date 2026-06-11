package com.bloomie.platform.dermatologyCare.domain.model.valueobjects;

import java.time.LocalTime;

public record TimeSlot(LocalTime startTime, LocalTime endTime) {
    private static final String START_TIME_NULL_KEY = "dermatology.time.slot.start.time.null";
    private static final String END_TIME_NULL_KEY = "dermatology.time.slot.end.time.null";
    private static final String START_TIME_GREATER = "dermatology.time.slot.end.time.greater";


    public TimeSlot {
        if (startTime == null){
            throw new IllegalArgumentException(START_TIME_NULL_KEY);
        }
        if (endTime == null) {
            throw new IllegalArgumentException(END_TIME_NULL_KEY);
        }
        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException(START_TIME_GREATER);
        }
    }
}
