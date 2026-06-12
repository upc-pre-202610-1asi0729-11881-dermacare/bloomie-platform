package com.bloomie.platform.iam.domain.model.aggregates;

import com.bloomie.platform.iam.domain.model.commands.ChangePasswordCommand;
import com.bloomie.platform.iam.domain.model.commands.RegisterDermatologistCommand;
import com.bloomie.platform.iam.domain.model.commands.RegisterUserCommand;
import com.bloomie.platform.iam.domain.model.commands.UpdateUserProfileCommand;
import com.bloomie.platform.iam.domain.model.events.DermatologistRegisteredEvent;
import com.bloomie.platform.iam.domain.model.events.PasswordChangedEvent;
import com.bloomie.platform.iam.domain.model.events.UserProfileUpdatedEvent;
import com.bloomie.platform.iam.domain.model.events.UserRegisteredEvent;
import com.bloomie.platform.iam.domain.model.valueobjects.UserRole;
import com.bloomie.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.bloomie.platform.iam.domain.model.entities.Role;
import com.bloomie.platform.iam.domain.model.valueobjects.HashedPassword;
import com.bloomie.platform.iam.domain.model.valueobjects.EmailAddress;
import com.bloomie.platform.iam.domain.model.valueobjects.PersonName;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Aggregate root representing an authenticated identity in the system.
 *
 * <p>Encapsulates the user's personal name, email address, hashed password,
 * and the set of roles that determine what the user is allowed to do.
 * Domain events are registered here and published after the aggregate is persisted.</p>
 */
public class User extends AbstractDomainAggregateRoot<User>{

    @Getter
    @Setter
    private Long id;

    private PersonName name;
    private EmailAddress emailAddress;
    private HashedPassword hashedPassword;

    private Set<Role> roles;

    /**
     * Full reconstitution constructor — used by the persistence assembler to rebuild
     * a user from stored data.
     */
    public User(Long id, PersonName name, EmailAddress email, HashedPassword hashedPassword, Set<Role> roles){
        this.id = id;
        this.name = name;
        this.emailAddress = email;
        this.hashedPassword = hashedPassword;
        this.roles = roles != null? roles : new HashSet<>();
    }

    /** Convenience constructor for a new user that does not yet have a persistence id. */
    public User(PersonName name, EmailAddress email, HashedPassword hashedPassword, Set<Role> roles) {
        this(null, name, email, hashedPassword, roles);
    }

    /** Primitive-field constructor that wraps each argument in its value object. */
    public User(String firstName, String lastName, String email, String hashedPassword, List<Role> roles) {
        this(
                new PersonName(firstName, lastName),
                new EmailAddress(email),
                new HashedPassword(hashedPassword),
                new HashSet<>(Role.validateRoleSet(roles))
        );
    }

    /** Creates a new Young Adult user from a {@link RegisterUserCommand}. */
    public User(RegisterUserCommand command, Role role) {
        this(
                command.firstName(),
                command.lastName(),
                command.email(),
                command.password(),
                List.of(role)
        );
    }

    /**
     * Registers the appropriate domain event based on the user's role.
     * Dermatologist users fire {@link DermatologistRegisteredEvent};
     * all others fire {@link UserRegisteredEvent}.
     */
    public void onRegistered() {
        if (roles.stream().anyMatch(r -> r.getName() == UserRole.ROLE_DERMATOLOGIST)) {
            registerDomainEvent(DermatologistRegisteredEvent.from(this));
        } else {
            registerDomainEvent(UserRegisteredEvent.from(this));
        }
    }

    /** Creates a new Dermatologist user from a {@link RegisterDermatologistCommand}. */
    public User(RegisterDermatologistCommand command, Role role){
        this(
                command.firstName(),
                command.lastName(),
                command.email(),
                command.password(),
                List.of(role)
        );
    }

    /** Registers and publishes a {@link DermatologistRegisteredEvent} after a dermatologist is created. */
    public void onDermatologistRegistered() {
        registerDomainEvent(DermatologistRegisteredEvent.from(this));
    }

    /** Replaces the user's name and email address with the values from the command. */
    public void updateProfile(UpdateUserProfileCommand command) {
        this.name = new PersonName(command.firstName(), command.lastName());
        this.emailAddress = new EmailAddress(command.email());
    }

    /** Registers and publishes a {@link UserProfileUpdatedEvent} after a profile update. */
    public void onProfileUpdate() {
        registerDomainEvent(UserProfileUpdatedEvent.from(this));
    }

    /**
     * Replaces the stored hash with the pre-hashed new password from the command.
     * The caller is responsible for verifying the current password and hashing the
     * new one before invoking this method.
     */
    public void changePassword(ChangePasswordCommand command) {
        this.hashedPassword = new HashedPassword(command.newPassword());
    }

    /** Registers and publishes a {@link PasswordChangedEvent} after a password change. */
    public void onPasswordChanged() {
        registerDomainEvent(PasswordChangedEvent.from(this));
    }

    /** Adds a single role to this user's role set and returns {@code this} for chaining. */
    public User addRole(Role role){
        this.roles.add(role);
        return this;
    }

    /** Adds multiple roles to this user's role set and returns {@code this} for chaining. */
    public User addRoles(List<Role> roles) {
        this.roles.addAll(roles);
        return this;
    }

    public PersonName getName() {return name;}
    public EmailAddress getEmailAddressValue() {return emailAddress;}
    public HashedPassword getHashedPassword() {return hashedPassword;}
    public Set<Role> getRoles() {return roles;}

    public String getFullName() {return name.getFullName();}
    public String getEmail() {return emailAddress.address();}
    public String getPassword() {return hashedPassword.value();}
}
