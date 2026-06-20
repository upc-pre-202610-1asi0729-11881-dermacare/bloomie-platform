package com.bloomie.platform.iam.domain.model.entities;

import com.bloomie.platform.iam.domain.model.valueobjects.UserRole;

import java.util.List;

/**
 * Domain entity representing a security role that can be assigned to a {@link com.bloomie.platform.iam.domain.model.aggregates.User}.
 *
 * <p>Roles are pre-seeded in the database and referenced by enum name.
 * The {@code id} may be {@code null} for transient role objects created
 * before they are associated with a persisted user.</p>
 */
public class Role {
    private Long id;
    private UserRole name;

    public Role() {}

    public Role(UserRole name) {
        this.name = name;
    }

    public Role(Long id, UserRole name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() { return id; }
    public UserRole getName() { return name; }

    public String getStringName() { return name.name(); }

    /** Returns the default role assigned to newly registered Young Adult users. */
    public static Role getDefaultRole() {
        return new Role(UserRole.ROLE_YOUNG_ADULT);
    }

    /** Creates a {@link Role} from its string enum name. Throws if the name is unknown. */
    public static Role toRoleFromName(String name) {
        return new Role(UserRole.valueOf(name));
    }

    /**
     * Guards against an empty or null role list by substituting the default role.
     * Used in the {@link com.bloomie.platform.iam.domain.model.aggregates.User} constructor.
     */
    public static List<Role> validateRoleSet(List<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return List.of(getDefaultRole());
        }
        return roles;
    }
}