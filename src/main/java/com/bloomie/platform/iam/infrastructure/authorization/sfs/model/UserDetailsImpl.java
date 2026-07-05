package com.bloomie.platform.iam.infrastructure.authorization.sfs.model;

import com.bloomie.platform.iam.domain.model.aggregates.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Spring Security {@link UserDetails} wrapper around the bloomie {@link User} aggregate.
 *
 * <p>The <em>username</em> field holds the user's <strong>email address</strong>,
 * which is the unique principal identifier used throughout the platform. Authorities
 * are derived from the user's {@link com.bloomie.platform.iam.domain.model.valueobjects.UserRole} enum values.</p>
 */
@Getter
@EqualsAndHashCode
public class UserDetailsImpl implements UserDetails {

    private final String username;

    @JsonIgnore
    private final String password;

    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

    private final Collection<? extends GrantedAuthority> authorities;

    /**
     * Creates a new instance with all required Spring Security fields.
     *
     * @param username    the user's email address (principal identifier)
     * @param password    the stored BCrypt hash (excluded from serialization)
     * @param authorities the set of granted authorities derived from the user's roles
     */
    public UserDetailsImpl(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }

    /**
     * Factory method that builds a {@link UserDetailsImpl} from a domain {@link User}.
     * The email address is used as the Spring Security principal name.
     *
     * @param user domain aggregate
     * @return populated {@link UserDetailsImpl} instance
     */
    public static UserDetailsImpl build(User user) {
        var authorities = user.getRoles().stream()
                .map(role -> role.getName().name())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new UserDetailsImpl(
                user.getEmail(),
                user.getPassword(),
                authorities);
    }
}
