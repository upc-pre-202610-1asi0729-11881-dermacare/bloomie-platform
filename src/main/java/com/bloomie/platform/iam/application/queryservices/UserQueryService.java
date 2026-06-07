package com.bloomie.platform.iam.application.queryservices;

import com.bloomie.platform.iam.domain.model.aggregates.User;
import com.bloomie.platform.iam.domain.model.queries.GetAllUsersQuery;
import com.bloomie.platform.iam.domain.model.queries.GetUserByEmailQuery;
import com.bloomie.platform.iam.domain.model.queries.GetUserByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service port for all read operations on the {@link User} aggregate.
 *
 * <p>Returns {@link java.util.Optional} for single-item lookups so callers can
 * decide how to handle the not-found case without exception-driven control flow.</p>
 */
public interface UserQueryService {
    /** Finds a user by their numeric identifier. */
    Optional<User> handle(GetUserByIdQuery query);

    /** Finds a user by their email address. */
    Optional<User> handle(GetUserByEmailQuery query);

    /** Returns all users registered in the system. */
    List<User> handle(GetAllUsersQuery query);
}
