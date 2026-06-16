package com.bloomie.platform.iam.application.acl;

import com.bloomie.platform.iam.application.queryservices.UserQueryService;
import com.bloomie.platform.iam.domain.model.aggregates.User;
import com.bloomie.platform.iam.domain.model.queries.GetUserByEmailQuery;
import com.bloomie.platform.iam.domain.model.queries.GetUserByIdQuery;
import com.bloomie.platform.iam.interfaces.acl.IamContextFacade;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link IamContextFacade}.
 *
 * <p>Allows other bounded contexts to query IAM data without depending directly
 * on IAM's internal application or domain types. Only primitive types cross
 * the Anti-Corruption Layer boundary.</p>
 */
@Service
public class IamContextFacadeImpl implements IamContextFacade {
    private final UserQueryService userQueryService;

    public IamContextFacadeImpl(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @Override
    public Long fetchUserByEmail(String email) {
        var query = new GetUserByEmailQuery(email);
        var user = userQueryService.handle(query);
        return user.isEmpty() ? 0L : user.get().getId();
    }

    @Override
    public boolean existsUserByEmail(String email) {
        var query = new GetUserByEmailQuery(email);
        return userQueryService.handle(query).isPresent();
    }

    @Override
    public boolean existsUserById(Long userId) {
        var query = new GetUserByIdQuery(userId);
        return userQueryService.handle(query).isPresent();
    }

    @Override
    public String fetchUserPhotoUrl(Long userId) {
        var query = new GetUserByIdQuery(userId);
        var user = userQueryService.handle(query);
        return user.isEmpty() ? null : user.get().getPhotoUrl();
    }
}
