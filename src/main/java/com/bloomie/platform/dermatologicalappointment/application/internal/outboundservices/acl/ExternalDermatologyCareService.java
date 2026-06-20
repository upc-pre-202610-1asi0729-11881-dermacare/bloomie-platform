package com.bloomie.platform.dermatologicalappointment.application.internal.outboundservices.acl;

/**
 * Outbound service interface used by this bounded context to query the Dermatology Care context.
 *
 * <p>The infrastructure layer provides the concrete implementation, which delegates to
 * {@link com.bloomie.platform.dermatologycare.interfaces.acl.DermatologyCareContextFacade}.
 * Only primitive types cross this boundary.</p>
 */
public interface ExternalDermatologyCareService {

    /**
     * Returns {@code true} if a dermatologist profile exists in the Dermatology Care context
     * for the given IAM user id.
     *
     * @param dermatologistId the IAM user id to check
     */
    boolean existsDermatologistProfile(Long dermatologistId);
}
