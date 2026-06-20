package com.bloomie.platform.iam.infrastructure.persistence.jpa.entities;

import com.bloomie.platform.iam.domain.model.valueobjects.UserRole;
import com.bloomie.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;

/**
 * JPA persistence entity for a role record in the {@code roles} table.
 *
 * <p>Stores the role name as a string using {@link EnumType#STRING} so that
 * database rows remain readable and are not tied to enum ordinal positions.</p>
 */
@Entity
@Table(name = "roles")
public class RolePersistenceEntity extends AuditableAbstractPersistenceEntity {
    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false, unique = true)
    private UserRole name;
    public RolePersistenceEntity() {}
    public RolePersistenceEntity(UserRole name) {
        this.name = name;
    }
    public UserRole getName() {return name;}
    public void setName(UserRole name) { this.name = name; }
}
