// AvailabilityPersistenceEntity.java
package com.bloomie.platform.dermatologyCare.infrastructure.persistence.jpa.entities;

import com.bloomie.platform.dermatologyCare.domain.model.valueobjects.DermatologistId;
import com.bloomie.platform.dermatologyCare.infrastructure.persistence.jpa.converters.DermatologistIdPersistenceConverter;
import com.bloomie.platform.dermatologyCare.infrastructure.persistence.jpa.embeddables.TimeSlotPersistenceEmbeddable;
import com.bloomie.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;

import java.time.DayOfWeek;

@Entity
@Table(name = "availabilities")
public class AvailabilityPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = DermatologistIdPersistenceConverter.class)
    @Column(name = "dermatologist_id", nullable = false)
    private DermatologistId dermatologistId;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek day;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "startTime", column = @Column(name = "start_time")),
            @AttributeOverride(name = "endTime", column = @Column(name = "end_time"))})
    private TimeSlotPersistenceEmbeddable timeSlot;

    @Column(name = "active", nullable = false)
    private boolean active;

    public AvailabilityPersistenceEntity() {}

    public DermatologistId getDermatologistId() { return dermatologistId; }
    public void setDermatologistId(DermatologistId dermatologistId) { this.dermatologistId = dermatologistId; }
    public DayOfWeek getDay() { return day; }
    public void setDay(DayOfWeek day) { this.day = day; }
    public TimeSlotPersistenceEmbeddable getTimeSlot() { return timeSlot; }
    public void setTimeSlot(TimeSlotPersistenceEmbeddable timeSlot) { this.timeSlot = timeSlot; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}