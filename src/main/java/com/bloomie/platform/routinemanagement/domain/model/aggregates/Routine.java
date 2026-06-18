package com.bloomie.platform.routinemanagement.domain.model.aggregates;

import com.bloomie.platform.routinemanagement.domain.model.commands.CreateRoutineCommand;
import com.bloomie.platform.routinemanagement.domain.model.valueobjects.RoutineStatus;
import com.bloomie.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Routine aggregate root.
 *
 * <p>
 * Represents a personalized skincare routine assigned to a user,
 * generated from their skin profile and facial scan.
 * </p>
 */
@Getter
public class Routine extends AbstractDomainAggregateRoot<Routine> {

    /**
     * The unique identifier for the routine.
     */
    @Setter
    private Long id;

    /**
     * The identifier of the user who owns this routine.
     */
    @Setter
    private Long userId;

    /**
     * The identifier of the skin profile used to generate this routine.
     */
    @Setter
    private Long skinProfileId;

    /**
     * The identifier of the facial scan used to generate this routine.
     */
    @Setter
    private Long facialScanId;

    /**
     * The current lifecycle status of the routine.
     */
    @Setter
    private RoutineStatus status;

    /**
     * The date and time when the routine was created.
     */
    @Setter
    private Date createdAt;

    /**
     * Default constructor for Routine.
     * Required for reconstruction from persistence.
     */
    public Routine() {
    }

    /**
     * Constructor for Routine from a {@link CreateRoutineCommand}.
     *
     * @param command the command containing initial routine data
     */
    public Routine(CreateRoutineCommand command) {
        this.userId = command.userId();
        this.skinProfileId = command.skinProfileId();
        this.facialScanId = command.facialScanId();
        this.status = command.status();
    }
}