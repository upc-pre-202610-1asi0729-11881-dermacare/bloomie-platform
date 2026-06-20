package com.bloomie.platform.iam.application.internal.commandservices;

import com.bloomie.platform.iam.application.commandservices.UserCommandService;
import com.bloomie.platform.iam.application.internal.outboundservices.hashing.HashingService;
import com.bloomie.platform.iam.domain.model.aggregates.User;
import com.bloomie.platform.iam.domain.model.commands.ChangePasswordCommand;
import com.bloomie.platform.iam.domain.model.commands.RegisterDermatologistCommand;
import com.bloomie.platform.iam.domain.model.commands.RegisterUserCommand;
import com.bloomie.platform.iam.domain.model.commands.UpdateUserPhotoCommand;
import com.bloomie.platform.iam.domain.model.commands.UpdateUserProfileCommand;
import com.bloomie.platform.iam.domain.model.valueobjects.EmailAddress;
import com.bloomie.platform.iam.domain.model.valueobjects.UserRole;
import com.bloomie.platform.iam.domain.repositories.UserRepository;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Application service that handles all write operations on the {@link User} aggregate.
 *
 * <p>Coordinates the {@link UserRepository} (domain port) and the {@link HashingService}
 * (outbound service) to register users, update profiles, and change passwords.
 * Domain events are registered and published by the repository adapter after each save.</p>
 */
@Service
public class UserCommandServiceImpl implements UserCommandService {
    private final UserRepository userRepository;
    private final HashingService hashingService;

    private static final String EMAIL_ALREADY_REGISTERED = "user.email.already.registered";
    private static final String USER_NOT_FOUND = "user.not.found";
    private static final String INVALID_PASSWORD = "user.password.incorrect";
    private static final String ROLE_NOT_FOUND = "user.rol.not.found";

    public UserCommandServiceImpl(UserRepository userRepository, HashingService hashingService) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
    }

    @Override
    public Result<User, ApplicationError> handle(RegisterUserCommand command) {
        if (userRepository.existsByEmailAddress(new EmailAddress(command.email())))
            return Result.failure(ApplicationError.conflict("user", EMAIL_ALREADY_REGISTERED));

        var role = userRepository.findRoleByName(UserRole.ROLE_YOUNG_ADULT)
                .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND));

        var hashedPassword = hashingService.encode(command.password());
        var user = new User(
                new RegisterUserCommand(command.email(), hashedPassword, command.firstName(), command.lastName()),
                role);

        return Result.success(userRepository.save(user));
    }

    @Override
    public Result<User, ApplicationError> handle(RegisterDermatologistCommand command) {
        if (userRepository.existsByEmailAddress(new EmailAddress(command.email())))
            return Result.failure(ApplicationError.conflict("user", EMAIL_ALREADY_REGISTERED));

        var role = userRepository.findRoleByName(UserRole.ROLE_DERMATOLOGIST)
                .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND));

        var hashedPassword = hashingService.encode(command.password());
        var user = new User(
                new RegisterDermatologistCommand(command.email(), hashedPassword, command.firstName(), command.lastName()),
                role);

        return Result.success(userRepository.save(user));
    }

    @Override
    public Result<User, ApplicationError> handle(UpdateUserProfileCommand command) {
        var user = userRepository.findById(Long.parseLong(command.userId()));
        if (user.isEmpty()) {
            return Result.failure(ApplicationError.notFound("user", USER_NOT_FOUND));
        }
        user.get().updateProfile(command);
        userRepository.save(user.get());
        user.get().onProfileUpdate();

        return Result.success(user.get());
    }

    @Override
    public Result<User, ApplicationError> handle(UpdateUserPhotoCommand command) {
        var user = userRepository.findById(command.userId());
        if (user.isEmpty()) {
            return Result.failure(ApplicationError.notFound("user", USER_NOT_FOUND));
        }
        user.get().updatePhoto(command);
        userRepository.save(user.get());
        user.get().onPhotoUpdated();

        return Result.success(user.get());
    }

    @Override
    public Result<Void, ApplicationError> handle(ChangePasswordCommand command) {
        var user = userRepository.findById(Long.parseLong(command.userId()));
        if (user.isEmpty())
            return Result.failure(ApplicationError.notFound("user", USER_NOT_FOUND));

        if (!hashingService.matches(command.currentPassword(), user.get().getPassword()))
            return Result.failure(ApplicationError.businessRuleViolation("user", INVALID_PASSWORD));

        var hashedNewPassword = hashingService.encode(command.newPassword());
        user.get().changePassword(new ChangePasswordCommand(command.userId(), command.currentPassword(), hashedNewPassword));
        userRepository.save(user.get());
        user.get().onPasswordChanged();

        return Result.success(null);
    }
}
