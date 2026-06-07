package com.bloomie.platform.iam.domain.model.valueobjects;

/**
 * Enumeration of all recognised security roles in the system.
 *
 * <p>Values must be prefixed with {@code ROLE_} to be compatible with
 * Spring Security's role-based access control conventions.</p>
 */
public enum UserRole {
    ROLE_YOUNG_ADULT,
    ROLE_DERMATOLOGIST
}
