package com.bloomie.platform.iam.interfaces.rest.transform;

import com.bloomie.platform.iam.domain.model.aggregates.User;
import com.bloomie.platform.iam.interfaces.rest.resources.AuthenticatedUserResource;

/**
 * Assembler that converts a sign-in result into an {@link AuthenticatedUserResource}.
 *
 * <p>In bloomie, the user's email address is used as the bearer-token subject, so
 * it is also surfaced in the response body to let the client know which identity
 * the token was issued for.</p>
 */
public class AuthenticatedUserResourceFromEntityAssembler {

    /**
     * Creates an {@link AuthenticatedUserResource} from the authenticated user aggregate
     * and the generated bearer token.
     *
     * @param user  authenticated {@link User} aggregate
     * @param token issued JWT bearer token
     * @return response resource with user id, email, and token
     */
    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        return new AuthenticatedUserResource(user.getId(), user.getEmail(), token);
    }
}
