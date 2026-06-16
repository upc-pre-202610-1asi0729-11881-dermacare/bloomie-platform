package com.bloomie.platform.dermatologicalappointment.application.internal.outboundservices.acl;

/**
 * Outbound service interface used by this bounded context to query the IAM context.
 *
 * <p>The infrastructure layer provides the concrete implementation, which delegates to
 * {@link com.bloomie.platform.iam.interfaces.acl.IamContextFacade}.
 * Only primitive types cross this boundary.</p>
 */
public interface ExternalIamService {

    /**
     * Returns {@code true} if a user with the given id is registered in the IAM context.
     *
     * @param userId the IAM user id to check
     */
    boolean existsUserById(Long userId);
}
