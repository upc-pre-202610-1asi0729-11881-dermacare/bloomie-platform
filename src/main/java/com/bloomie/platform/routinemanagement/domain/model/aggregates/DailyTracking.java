package com.bloomie.platform.routinemanagement.domain.model.aggregates;

import com.bloomie.platform.routinemanagement.domain.model.valueobjects.TrackingStatus;
import com.bloomie.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DailyTracking aggregate root.
 *
 * <p>
 * Represents the daily completion record of a skincare routine for a given user.
 * Tracks whether a routine was completed or not on a specific date.
 * </p>
 */
@Getter
public class DailyTracking extends AbstractDomainAggregateRoot<DailyTracking> {

    @Setter
    private Long id;

    @Setter
    private Long routineId;

    @Setter
    private Long userId;

    @Setter
    private LocalDate date;

    @Setter
    private TrackingStatus status;

    public DailyTracking() {
    }
}