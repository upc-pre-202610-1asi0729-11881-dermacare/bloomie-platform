package com.bloomie.platform.iam.domain.model.queries;

/**
 * Query that retrieves a single user by their email address.
 *
 * <p>The email is passed as a plain {@link String} so that the domain record
 * stays free of value-object dependencies. The query service wraps it in an
 * {@link com.bloomie.platform.iam.domain.model.valueobjects.EmailAddress} before
 * delegating to the repository.</p>
 *
 * @param email the raw email address to search for
 */
public record GetUserByEmailQuery(String email) {
}
