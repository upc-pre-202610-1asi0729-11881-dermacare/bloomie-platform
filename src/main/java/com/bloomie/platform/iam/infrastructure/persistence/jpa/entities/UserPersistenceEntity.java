package com.bloomie.platform.iam.infrastructure.persistence.jpa.entities;

import com.bloomie.platform.iam.domain.model.valueobjects.EmailAddress;
import com.bloomie.platform.iam.infrastructure.persistence.jpa.converters.EmailAddressPersistenceConverter;
import com.bloomie.platform.iam.infrastructure.persistence.jpa.converters.HashedPasswordPersistenceConverter;
import com.bloomie.platform.iam.domain.model.valueobjects.HashedPassword;
import com.bloomie.platform.iam.infrastructure.persistence.jpa.embeddables.PersonNamePersistenceEmbeddable;
import com.bloomie.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "first_name")),
            @AttributeOverride(name = "lastName", column = @Column(name = "last_name"))})
    private PersonNamePersistenceEmbeddable name;

    @Convert(converter = EmailAddressPersistenceConverter.class)
    @Column(name = "email_address", nullable = false, unique = true)
    private EmailAddress emailAddress;

    @Convert(converter = HashedPasswordPersistenceConverter.class)
    @Column(name = "hashed_password", nullable = false)
    private HashedPassword hashedPassword;

    @Column(name = "photo_url", columnDefinition = "LONGTEXT")
    private String photoUrl;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RolePersistenceEntity> roles = new HashSet<>();

    public UserPersistenceEntity() {
    }

    public PersonNamePersistenceEmbeddable getName() {
        return name;
    }

    public void setName(PersonNamePersistenceEmbeddable name) {
        this.name = name;
    }

    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
    }

    public HashedPassword getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(HashedPassword hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Set<RolePersistenceEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RolePersistenceEntity> roles) {
        this.roles = roles;
    }
}