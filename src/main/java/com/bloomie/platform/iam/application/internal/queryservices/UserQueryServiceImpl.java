package com.bloomie.platform.iam.application.internal.queryservices;

import com.bloomie.platform.iam.application.queryservices.UserQueryService;
import com.bloomie.platform.iam.domain.model.aggregates.User;
import com.bloomie.platform.iam.domain.model.queries.GetAllUsersQuery;
import com.bloomie.platform.iam.domain.model.queries.GetUserByEmailQuery;
import com.bloomie.platform.iam.domain.model.queries.GetUserByIdQuery;
import com.bloomie.platform.iam.domain.model.valueobjects.EmailAddress;
import com.bloomie.platform.iam.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service that handles all read operations on the {@link User} aggregate.
 *
 * <p>Delegates to {@link UserRepository} and wraps raw email strings in
 * {@link com.bloomie.platform.iam.domain.model.valueobjects.EmailAddress} value objects
 * before querying, so validation is enforced at the boundary.</p>
 */
@Service
public class UserQueryServiceImpl implements UserQueryService {
    private final UserRepository userRepository;
    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        return userRepository.findById(query.userId());
    }

    @Override
    public Optional<User> handle(GetUserByEmailQuery query) {
        return userRepository.findByEmailAddress(new EmailAddress(query.email()));
    }

    @Override
    public List<User> handle(GetAllUsersQuery query) {
        return userRepository.findAll();
    }
}
