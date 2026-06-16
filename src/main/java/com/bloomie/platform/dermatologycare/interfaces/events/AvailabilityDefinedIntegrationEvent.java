package com.bloomie.platform.dermatologycare.interfaces.events;

import com.bloomie.platform.dermatologycare.domain.model.aggregates.Availability;

/**
 * Integration event published by the {@code dermatologyCare} bounded context when a new
 * {@link Availability} slot has been defined and persisted.
 *
 * <p>This is the <em>published language</em> of the dermatology care context.
 * Other bounded contexts (e.g. Dermatological Appointment) should listen to this event
 * rather than to the internal {@link com.bloomie.platform.dermatologycare.domain.model.events.AvailabilityDefinedEvent}.</p>
 *
 * @param availabilityId   the persistence id of the new availability slot
 * @param dermatologistId  the IAM user id of the dermatologist
 * @param dayOfWeek        day of the week as a string (e.g. "MONDAY")
 * @param startTime        start time as ISO-8601 string (e.g. "09:00")
 * @param endTime          end time as ISO-8601 string (e.g. "17:00")
 */
public record AvailabilityDefinedIntegrationEvent(
        Long availabilityId,
        Long dermatologistId,
        String dayOfWeek,
        String startTime,
        String endTime) {

    public static AvailabilityDefinedIntegrationEvent from(Availability availability) {
        return new AvailabilityDefinedIntegrationEvent(
                availability.getId(),
                availability.getDermatologistId(),
                availability.getDayofWeek().name(),
                availability.getTimeSlot().startTime().toString(),
                availability.getTimeSlot().endTime().toString());
    }
}
