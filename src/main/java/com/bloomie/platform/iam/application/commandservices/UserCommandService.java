package com.bloomie.platform.iam.application.commandservices;

import com.bloomie.platform.iam.domain.model.aggregates.User;
import com.bloomie.platform.iam.domain.model.commands.ChangePasswordCommand;
import com.bloomie.platform.iam.domain.model.commands.RegisterDermatologistCommand;
import com.bloomie.platform.iam.domain.model.commands.RegisterUserCommand;
import com.bloomie.platform.iam.domain.model.commands.UpdateUserProfileCommand;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;

/**
 * Application service port for all write operations on the {@link User} aggregate.
 *
 * <p>Every handler returns a {@link Result} so callers can branch on success or failure
 * without relying on exceptions for expected business-rule violations (e.g. duplicate email).</p>
 */
public interface UserCommandService {
    /** Registers a new Young Adult user and returns the created aggregate on success. */
    Result<User, ApplicationError> handle(RegisterUserCommand command);

    /** Registers a new Dermatologist user and returns the created aggregate on success. */
    Result<User, ApplicationError> handle(RegisterDermatologistCommand command);

    /** Updates the profile (name and email) of an existing user. */
    Result<User, ApplicationError> handle(UpdateUserProfileCommand command);

    /** Changes the password of an existing user after verifying the current one. */
    Result<Void, ApplicationError> handle(ChangePasswordCommand command);
}
