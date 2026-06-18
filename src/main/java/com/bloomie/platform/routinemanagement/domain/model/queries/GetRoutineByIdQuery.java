package com.bloomie.platform.routinemanagement.domain.model.queries;

/**
 * Query to get a routine by its unique identifier.
 *
 * @param routineId the routine identifier. Cannot be null or less than 1.
 */
public record GetRoutineByIdQuery(Long routineId) {

    public GetRoutineByIdQuery {
        if (routineId == null || routineId < 1)
            throw new IllegalArgumentException("routineId cannot be null or less than 1");
    }
}