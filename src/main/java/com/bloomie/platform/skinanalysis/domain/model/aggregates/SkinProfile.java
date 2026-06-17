package com.bloomie.platform.skinanalysis.domain.model.aggregates;

import com.bloomie.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.bloomie.platform.skinanalysis.domain.model.commands.CompleteSkinProfileCommand;
import com.bloomie.platform.skinanalysis.domain.model.commands.UpdateSkinCharacteristicsCommand;
import com.bloomie.platform.skinanalysis.domain.model.events.SkinCharacteristicsUpdatedEvent;
import com.bloomie.platform.skinanalysis.domain.model.events.SkinProfileCompletedEvent;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.PatientId;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.Sensitivity;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.SkinProfileStatus;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.SkinType;
import lombok.Getter;
import lombok.Setter;

/**
 * Aggregate root representing the skin profile of a patient.
 *
 * <p>Created in {@code COMPLETED} status when the patient submits the onboarding
 * skin questionnaire. Characteristics can be updated afterwards via
 * {@link #update(UpdateSkinCharacteristicsCommand)}.</p>
 *
 * <p>Domain events are published by the repository adapter after each save.</p>
 */
public class SkinProfile extends AbstractDomainAggregateRoot<SkinProfile> {

    @Getter
    @Setter
    private Long id;

    @Getter
    private PatientId patientId;

    @Getter
    private SkinType skinType;

    @Getter
    private Sensitivity sensitivity;

    @Getter
    private String waterIntake;

    @Getter
    private String sunExposure;

    @Getter
    private String sleepHours;

    @Getter
    private SkinProfileStatus status;

    /** Creates a new, completed skin profile from the patient's questionnaire answers. */
    public SkinProfile(CompleteSkinProfileCommand command) {
        this.patientId   = new PatientId(command.patientId());
        this.skinType    = SkinType.valueOf(command.skinType().toUpperCase());
        this.sensitivity = Sensitivity.valueOf(command.sensitivity().toUpperCase());
        this.waterIntake = command.waterIntake();
        this.sunExposure = command.sunExposure();
        this.sleepHours  = command.sleepHours();
        this.status      = SkinProfileStatus.COMPLETED;
    }

    /** Reconstitution constructor — used by the persistence assembler; skips business validation. */
    public SkinProfile(Long id, PatientId patientId, SkinType skinType, Sensitivity sensitivity,
                       String waterIntake, String sunExposure, String sleepHours, SkinProfileStatus status) {
        this.id          = id;
        this.patientId   = patientId;
        this.skinType    = skinType;
        this.sensitivity = sensitivity;
        this.waterIntake = waterIntake;
        this.sunExposure = sunExposure;
        this.sleepHours  = sleepHours;
        this.status      = status;
    }

    /** Updates all skin characteristics from the given command. */
    public void update(UpdateSkinCharacteristicsCommand command) {
        this.skinType    = SkinType.valueOf(command.skinType().toUpperCase());
        this.sensitivity = Sensitivity.valueOf(command.sensitivity().toUpperCase());
        this.waterIntake = command.waterIntake();
        this.sunExposure = command.sunExposure();
        this.sleepHours  = command.sleepHours();
    }

    /** Registers a {@link SkinProfileCompletedEvent} after a new profile is persisted. */
    public void onCompleted() {
        registerDomainEvent(SkinProfileCompletedEvent.from(this));
    }

    /** Registers a {@link SkinCharacteristicsUpdatedEvent} after characteristics are updated and persisted. */
    public void onUpdated() {
        registerDomainEvent(SkinCharacteristicsUpdatedEvent.from(this));
    }
}
