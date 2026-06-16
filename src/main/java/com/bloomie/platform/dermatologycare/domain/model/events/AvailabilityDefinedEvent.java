package com.bloomie.platform.dermatologycare.domain.model.events;

import com.bloomie.platform.dermatologycare.domain.model.aggregates.Availability;

public record AvailabilityDefinedEvent(Long availabilityId, Long dermatologistId, String dayOfWeek, String startTime, String endTime) {
    public static AvailabilityDefinedEvent from(Availability availability){
        return new AvailabilityDefinedEvent(
                availability.getId(),
                availability.getDermatologistId(),
                availability.getDayofWeek().name(),
                availability.getTimeSlot().startTime().toString(),
                availability.getTimeSlot().endTime().toString()
        );
    }
}
