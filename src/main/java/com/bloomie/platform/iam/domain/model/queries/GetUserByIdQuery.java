package com.bloomie.platform.iam.domain.model.queries;

/**
 * Query that retrieves a single user by their numeric identifier.
 *
 * @param userId the unique identifier of the user to look up
 */
public record GetUserByIdQuery(Long userId) {
}
