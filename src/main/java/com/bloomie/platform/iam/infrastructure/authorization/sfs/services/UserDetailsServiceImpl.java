package com.bloomie.platform.iam.infrastructure.authorization.sfs.services;

import com.bloomie.platform.iam.domain.model.valueobjects.EmailAddress;
import com.bloomie.platform.iam.domain.repositories.UserRepository;
import com.bloomie.platform.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Spring Security {@link UserDetailsService} that loads users by their email address.
 *
 * <p>In bloomie the email is the unique principal identifier, so the {@code username}
 * parameter received by {@link #loadUserByUsername} is treated as an email address.
 * The bean name {@code "defaultUserDetailsService"} is required so that
 * {@link com.bloomie.platform.iam.infrastructure.authorization.sfs.configuration.WebSecurityConfiguration}
 * can inject it unambiguously via {@code @Qualifier}.</p>
 */
@Service("defaultUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads a {@link UserDetails} by the user's email address.
     *
     * @param email the user's email address (plays the role of "username" in Spring Security)
     * @return populated {@link UserDetailsImpl}
     * @throws UsernameNotFoundException if no user exists with the given email
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmailAddress(new EmailAddress(email))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return UserDetailsImpl.build(user);
    }
}
