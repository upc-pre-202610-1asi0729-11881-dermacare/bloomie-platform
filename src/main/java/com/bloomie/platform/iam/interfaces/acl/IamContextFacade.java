package com.bloomie.platform.iam.interfaces.acl;

/**
 * Anti-Corruption Layer facade exposed by the IAM bounded context.
 *
 * <p>Other bounded contexts use this interface to query IAM data without coupling
 * themselves to IAM's internal domain types or application services. Only primitive
 * types cross this boundary to keep the contract stable and context-agnostic.</p>
 */
public interface IamContextFacade {

    /**
     * Returns the id of the user with the given email, or {@code 0} if not found.
     *
     * @param email the email address to look up
     * @return the user's id, or {@code 0} if no match exists
     */
    Long fetchUserByEmail(String email);

    /**
     * Returns {@code true} if a user with the given email is registered.
     *
     * @param email the email address to check
     */
    boolean existsUserByEmail(String email);

    /**
     * Returns {@code true} if a user with the given id exists.
     *
     * @param userId the numeric user id to check
     */
    boolean existsUserById(Long userId);

    /**
     * Returns the profile photo URL of the user with the given id,
     * or {@code null} if the user does not exist or has no photo set.
     *
     * @param userId the numeric user id to look up
     * @return the user's photo URL, or {@code null} if not available
     */
    String fetchUserPhotoUrl(Long userId);
}
