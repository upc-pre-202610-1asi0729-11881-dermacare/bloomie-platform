package com.bloomie.platform.dermatologyCare.domain.model.events;

import com.bloomie.platform.dermatologyCare.domain.model.aggregates.Availability;

public record AvailabilityUpdatedEvent(Long availabilityId, Long dermatologistId, String dayOfWeek, String startTime, String endTime) {
    public static AvailabilityUpdatedEvent from(Availability availability) {
        return new AvailabilityUpdatedEvent(
                availability.getId(),
                availability.getDermatologistId(),
                availability.getDayofWeek().name(),
                availability.getTimeSlot().startTime().toString(),
                availability.getTimeSlot().endTime().toString()
        );
    }
}
