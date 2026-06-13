package com.bloomie.platform.dermatologyCare.application.internal.outboundservices.acl;

import com.bloomie.platform.dermatologyCare.domain.model.valueobjects.DermatologistId;
import com.bloomie.platform.iam.interfaces.acl.IamContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Outbound ACL service that wraps {@link IamContextFacade} to provide
 * IAM lookups in terms of Dermatology Care's own value objects.
 */
@Service("dermatologyCareExternalIamService")
public class ExternalIamService {

    private final IamContextFacade iamContextFacade;

    public ExternalIamService(IamContextFacade iamContextFacade) {
        this.iamContextFacade = iamContextFacade;
    }

    /**
     * Returns the {@link DermatologistId} for the given user id if the user exists in IAM.
     *
     * @param userId the IAM user id
     * @return an Optional containing the DermatologistId, or empty if not found
     */
    public Optional<DermatologistId> fetchDermatologistById(Long userId) {
        return iamContextFacade.existsUserById(userId)
                ? Optional.of(new DermatologistId(userId))
                : Optional.empty();
    }

    /**
     * Returns the {@link DermatologistId} for the dermatologist registered with the given email.
     *
     * @param email the IAM email address
     * @return an Optional containing the DermatologistId, or empty if not found
     */
    public Optional<DermatologistId> fetchDermatologistByEmail(String email) {
        var userId = iamContextFacade.fetchUserByEmail(email);
        return userId == 0L ? Optional.empty() : Optional.of(new DermatologistId(userId));
    }
}
