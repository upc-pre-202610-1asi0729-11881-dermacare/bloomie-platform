package com.bloomie.platform.dermatologyCare.interfaces.acl;

/**
 * Anti-Corruption Layer facade exposed by the Dermatology Care bounded context.
 *
 * <p>Other bounded contexts use this interface to query dermatology data without
 * coupling to internal domain types. Only primitive types cross this boundary.</p>
 */
public interface DermatologyCareContextFacade {

    /**
     * Returns {@code true} if a dermatologist profile exists for the given IAM user id.
     *
     * @param dermatologistId the IAM user id
     */
    boolean existsDermatologistProfileByDermatologistId(Long dermatologistId);

    /**
     * Returns the profile's own persistence id for the given IAM user id, or {@code 0} if not found.
     *
     * @param dermatologistId the IAM user id
     */
    Long fetchProfileIdByDermatologistId(Long dermatologistId);
}
