package com.bloomie.platform.dermatologycare.infrastructure.persistence.jpa.entities;

import com.bloomie.platform.dermatologycare.domain.model.valueobjects.ContactPhone;
import com.bloomie.platform.dermatologycare.domain.model.valueobjects.DermatologistId;
import com.bloomie.platform.dermatologycare.domain.model.valueobjects.LicenseNumber;
import com.bloomie.platform.dermatologycare.domain.model.valueobjects.SpecialtyName;
import com.bloomie.platform.dermatologycare.infrastructure.persistence.jpa.converters.ContactPhonePersistenceConverter;
import com.bloomie.platform.dermatologycare.infrastructure.persistence.jpa.converters.DermatologistIdPersistenceConverter;
import com.bloomie.platform.dermatologycare.infrastructure.persistence.jpa.converters.LicenseNumberPersistenceConverter;
import com.bloomie.platform.dermatologycare.infrastructure.persistence.jpa.converters.SpecialtyNamePersistenceConverter;
import com.bloomie.platform.dermatologycare.infrastructure.persistence.jpa.embeddables.PersonNamePersistenceEmbeddable;
import com.bloomie.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;

@Entity
@Table(name= "dermatologist_profiles")
public class DermatologistProfilePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "first_name")),
            @AttributeOverride(name = "lastName", column = @Column(name = "last_name"))})
    private PersonNamePersistenceEmbeddable name;

    @Convert(converter = DermatologistIdPersistenceConverter.class)
    @Column(name = "dermatologist_id", nullable = false, unique = true)
    private DermatologistId dermatologistId;

    @Convert(converter = SpecialtyNamePersistenceConverter.class)
    @Column(name = "specialty_name")
    private SpecialtyName specialty;

    @Convert(converter = LicenseNumberPersistenceConverter.class)
    @Column(name = "license_number")
    private LicenseNumber licenseNumber;

    @Convert(converter = ContactPhonePersistenceConverter.class)
    @Column(name = "contact_phone")
    private ContactPhone contactPhone;

    @Column(name = "biography", columnDefinition = "TEXT")
    private String biography;

    public DermatologistProfilePersistenceEntity() {}

    public DermatologistId getDermatologistId() { return dermatologistId; }
    public void setDermatologistId(DermatologistId dermatologistId) { this.dermatologistId = dermatologistId; }
    public PersonNamePersistenceEmbeddable getName() { return name; }
    public void setName(PersonNamePersistenceEmbeddable name) { this.name = name; }
    public SpecialtyName getSpecialty() { return specialty; }
    public void setSpecialty(SpecialtyName specialty) { this.specialty = specialty; }
    public LicenseNumber getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(LicenseNumber licenseNumber) { this.licenseNumber = licenseNumber; }
    public ContactPhone getContactPhone() { return contactPhone; }
    public void setContactPhone(ContactPhone contactPhone) { this.contactPhone = contactPhone; }
    public String getBiography() { return biography; }
    public void setBiography(String biography) { this.biography = biography; }
}
