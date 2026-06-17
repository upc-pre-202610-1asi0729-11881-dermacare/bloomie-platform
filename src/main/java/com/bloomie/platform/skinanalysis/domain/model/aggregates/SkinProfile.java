package com.bloomie.platform.skinAnalysis.domain.model.aggregates;

import com.bloomie.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.bloomie.platform.skinAnalysis.domain.model.commands.CompleteSkinProfileCommand;
import com.bloomie.platform.skinAnalysis.domain.model.events.SkinProfileCompletedEvent;
import com.bloomie.platform.skinAnalysis.domain.model.valueobjects.PatientId;
import com.bloomie.platform.skinAnalysis.domain.model.valueobjects.SkinConcerns;
import com.bloomie.platform.skinAnalysis.domain.model.valueobjects.SkinProfileStatus;
import com.bloomie.platform.skinAnalysis.domain.model.valueobjects.SkinTone;
import com.bloomie.platform.skinAnalysis.domain.model.valueobjects.SkinType;
import lombok.Getter;
import lombok.Setter;

/**
 * Aggregate root representing the skin profile of a patient.
 *
 * <p>A skin profile is created when the patient completes the onboarding
 * skin questionnaire. The lifecycle is straightforward:
 * {@code PENDING → COMPLETED} (single transition on creation).</p>
 *
 * <p>Domain events are published by the repository adapter after persisting.</p>
 */
public class SkinProfile extends AbstractDomainAggregateRoot<SkinProfile> {

    private static final String ALREADY_COMPLETED_KEY = "skin_analysis.skin_profile.already_completed";

    @Getter
    @Setter
    private Long id;

    @Getter
    private PatientId patientId;

    @Getter
    private SkinType skinType;

    @Getter
    private SkinTone skinTone;

    @Getter
    private SkinConcerns skinConcerns;

    @Getter
    private SkinProfileStatus status;

    /**
     * Creates a new, completed skin profile from the command data.
     * Validates that all required fields are present and consistent.
     */
    public SkinProfile(CompleteSkinProfileCommand command) {
        this.patientId   = new PatientId(command.patient_id());
        this.skinType    = SkinType.valueOf(command.skin_type().toUpperCase());
        this.skinTone    = SkinTone.valueOf(command.skin_tone().toUpperCase());
        this.skinConcerns = new SkinConcerns(command.concerns());
        this.status      = SkinProfileStatus.COMPLETED;
    }

    /**
     * Reconstitution constructor — used by the persistence assembler; skips business validation.
     */
    public SkinProfile(Long id, PatientId patientId, SkinType skinType,
                       SkinTone skinTone, SkinConcerns skinConcerns, SkinProfileStatus status) {
        this.id           = id;
        this.patientId    = patientId;
        this.skinType     = skinType;
        this.skinTone     = skinTone;
        this.skinConcerns = skinConcerns;
        this.status       = status;
    }

    /**
     * Called by the repository adapter after persisting a new skin profile (id is set first).
     * Registers {@link SkinProfileCompletedEvent}.
     */
    public void onCompleted() {
        registerDomainEvent(SkinProfileCompletedEvent.from(this));
    }
}
