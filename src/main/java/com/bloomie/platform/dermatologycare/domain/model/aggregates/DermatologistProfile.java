package com.bloomie.platform.dermatologycare.domain.model.aggregates;

import com.bloomie.platform.dermatologycare.domain.model.commands.RegisterDermatologistProfileCommand;
import com.bloomie.platform.dermatologycare.domain.model.commands.UpdateDermatologistProfileCommand;
import com.bloomie.platform.dermatologycare.domain.model.events.DermatologistProfileRegisteredEvent;
import com.bloomie.platform.dermatologycare.domain.model.events.DermatologistProfileUpdatedEvent;
import com.bloomie.platform.dermatologycare.domain.model.valueobjects.ContactPhone;
import com.bloomie.platform.dermatologycare.domain.model.valueobjects.DermatologistId;
import com.bloomie.platform.dermatologycare.domain.model.valueobjects.LicenseNumber;
import com.bloomie.platform.dermatologycare.domain.model.valueobjects.PersonName;
import com.bloomie.platform.dermatologycare.domain.model.valueobjects.SpecialtyName;
import com.bloomie.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

/**
 * Aggregate root for a dermatologist's professional profile.
 *
 * <p>Created automatically when IAM emits a {@code DermatologistRegisteredIntegrationEvent}.
 * Specialty, license number, contact phone, and consultation fee are optional at creation
 * and filled in via {@link UpdateDermatologistProfileCommand}.</p>
 */
public class DermatologistProfile extends AbstractDomainAggregateRoot<DermatologistProfile> {

    @Setter
    @Getter
    private Long id;

    private DermatologistId dermatologistId;
    private PersonName name;
    private SpecialtyName specialtyName;
    private LicenseNumber licenseNumber;
    private ContactPhone contactPhone;
    private String biography;
    private Double consultationFee;

    /** Creates a minimal profile from the IAM integration event payload. Fee defaults to 0.0 until set by the dermatologist. */
    public DermatologistProfile(RegisterDermatologistProfileCommand command) {
        this.dermatologistId = command.dermatologistId();
        this.name = new PersonName(command.firstName(), command.lastName());
        this.consultationFee = 0.0;
    }

    /** Full reconstitution constructor used by the persistence assembler. */
    public DermatologistProfile(Long id, DermatologistId dermatologistId, PersonName personName,
                                SpecialtyName specialtyName, LicenseNumber licenseNumber,
                                ContactPhone contactPhone, String biography, Double consultationFee) {
        this.id = id;
        this.dermatologistId = dermatologistId;
        this.name = personName;
        this.specialtyName = specialtyName;
        this.licenseNumber = licenseNumber;
        this.contactPhone = contactPhone;
        this.biography = biography;
        this.consultationFee = consultationFee;
    }

    /** Updates all editable fields. All VO fields are required for an update. */
    public void update(UpdateDermatologistProfileCommand command) {
        this.name = new PersonName(command.firstName(), command.lastName());
        this.specialtyName = new SpecialtyName(command.specialty());
        this.licenseNumber = new LicenseNumber(command.licenseNumber());
        this.contactPhone = new ContactPhone(command.phone());
        this.biography = command.biography();
        this.consultationFee = command.consultationFee();
    }

    /** Registers a {@link DermatologistProfileRegisteredEvent} after a new profile is persisted. */
    public void onRegistered() {
        registerDomainEvent(DermatologistProfileRegisteredEvent.from(this));
    }

    /** Registers a {@link DermatologistProfileUpdatedEvent} after a profile update is persisted. */
    public void onUpdated() {
        registerDomainEvent(DermatologistProfileUpdatedEvent.from(this));
    }

    public Long getDermatologistId() { return dermatologistId.dermatologistId(); }
    public DermatologistId getDermatologistIdValue() { return dermatologistId; }
    public String getFullName() { return name.getFullName(); }
    public PersonName getName() { return name; }
    public SpecialtyName getSpecialty() { return specialtyName; }
    public LicenseNumber getLicenseNumber() { return licenseNumber; }
    public ContactPhone getContactPhone() { return contactPhone; }
    public String getBiography() { return biography; }
    public Double getConsultationFee() { return consultationFee; }
}
