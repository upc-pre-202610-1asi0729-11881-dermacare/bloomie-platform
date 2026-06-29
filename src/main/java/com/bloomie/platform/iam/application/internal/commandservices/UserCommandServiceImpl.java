package com.bloomie.platform.iam.application.internal.commandservices;

import com.bloomie.platform.iam.application.commandservices.UserCommandService;
import com.bloomie.platform.iam.application.internal.outboundservices.hashing.HashingService;
import com.bloomie.platform.iam.application.internal.outboundservices.tokens.TokenService;
import com.bloomie.platform.iam.domain.model.aggregates.User;
import com.bloomie.platform.iam.domain.model.commands.ChangePasswordCommand;
import com.bloomie.platform.iam.domain.model.commands.RegisterDermatologistCommand;
import com.bloomie.platform.iam.domain.model.commands.RegisterUserCommand;
import com.bloomie.platform.iam.domain.model.commands.SignInCommand;
import com.bloomie.platform.iam.domain.model.commands.UpdateUserPhotoCommand;
import com.bloomie.platform.iam.domain.model.commands.UpdateUserProfileCommand;
import com.bloomie.platform.iam.domain.model.valueobjects.EmailAddress;
import com.bloomie.platform.iam.domain.model.valueobjects.UserRole;
import com.bloomie.platform.iam.domain.repositories.RoleRepository;
import com.bloomie.platform.iam.domain.repositories.UserRepository;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

/**
 * Application service that handles all write operations on the {@link User} aggregate.
 *
 * <p>Coordinates {@link UserRepository}, {@link RoleRepository}, {@link HashingService},
 * and {@link TokenService} to register users, authenticate them, update profiles,
 * and change passwords. Domain events are published by the repository adapter after save.</p>
 */
@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;

    private static final String EMAIL_ALREADY_REGISTERED = "user.email.already.registered";
    private static final String USER_NOT_FOUND = "user.not.found";
    private static final String INVALID_CREDENTIALS = "user.credentials.invalid";
    private static final String INVALID_PASSWORD = "user.password.incorrect";
    private static final String ROLE_NOT_FOUND = "user.rol.not.found";

    public UserCommandServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            HashingService hashingService,
            TokenService tokenService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
    }

    /**
     * Authenticates the user by email and verifies the password against the stored hash.
     * On success, generates a JWT token using the user's email as the subject.
     *
     * @param command sign-in command with raw email and plain-text password
     * @return pair of authenticated {@link User} and the issued token, or an error
     */
    @Override
    public Result<ImmutablePair<User, String>, ApplicationError> handle(SignInCommand command) {
        var user = userRepository.findByEmailAddress(new EmailAddress(command.email()));
        if (user.isEmpty()) {
            return Result.failure(ApplicationError.notFound("user", USER_NOT_FOUND));
        }
        if (!hashingService.matches(command.password(), user.get().getPassword())) {
            return Result.failure(ApplicationError.validationError("credentials", INVALID_CREDENTIALS));
        }
        // Email is used as the JWT subject (it is the unique identifier in bloomie)
        var token = tokenService.generateToken(user.get().getEmail());
        return Result.success(ImmutablePair.of(user.get(), token));
    }

    /**
     * Registers a new Young Adult user after verifying the email is not already taken.
     * The plain-text password is hashed before the aggregate is persisted.
     *
     * @param command registration command with email, plain-text password, and name
     * @return created {@link User} aggregate, or a conflict error if email already exists
     */
    @Override
    public Result<User, ApplicationError> handle(RegisterUserCommand command) {
        if (userRepository.existsByEmailAddress(new EmailAddress(command.email())))
            return Result.failure(ApplicationError.conflict("user", EMAIL_ALREADY_REGISTERED));

        var role = roleRepository.findByName(UserRole.ROLE_YOUNG_ADULT)
                .orElseThrow(() -> new RuntimeException(ROLE_NOT_FOUND));

        var hashedPassword = hashingService.encode(command.password());
        var user = new User(
                new RegisterUserCommand(command.email(), hashedPassword, command.firstName(), command.lastName()),
                role);

        return Result.success(userRepository.save(user));
    }

    /**
     * Registers a new Dermatologist user after verifying the email is not already taken.
     * The plain-text password is hashed before the aggregate is persisted.
     *
     * @param command registration command with email, plain-text password, and name
     * @return created {@link User} aggregate, or a conflict error if email already exists
     */
    @Override
    public Result<User, ApplicationError> handle(RegisterDermatologistCommand command) {
        if (userRepository.existsByEmailAddress(new EmailAddress(command.email())))
            return Result.failure(ApplicationError.conflict("user", EMAIL_ALREADY_REGISTERED));

        var role = roleRepository.findByName(UserRole.ROLE_DERMATOLOGIST)
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
