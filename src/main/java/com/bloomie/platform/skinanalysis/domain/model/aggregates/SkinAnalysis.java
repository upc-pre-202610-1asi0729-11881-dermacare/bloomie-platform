package com.bloomie.platform.skinanalysis.domain.model.aggregates;

import com.bloomie.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.bloomie.platform.skinanalysis.domain.model.commands.AnalyzeSkinScanCommand;
import com.bloomie.platform.skinanalysis.domain.model.events.PreliminaryDiagnosisGeneratedEvent;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.FacialScanId;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.PatientId;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.SkinAnalysisStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Aggregate root representing the result of analysing a patient's facial scan.
 *
 * <p>Created in {@code COMPLETED} status when the system processes the skin analysis
 * triggered by a submitted facial scan. Scores are computed deterministically from
 * the patient's skin type and sensitivity data.</p>
 *
 * <p>Domain events are published by the repository adapter after each save.</p>
 */
public class SkinAnalysis extends AbstractDomainAggregateRoot<SkinAnalysis> {

    @Getter
    @Setter
    private Long id;

    private PatientId patientId;

    private FacialScanId facialScanId;

    @Getter
    private String skinType;

    @Getter
    private Double overallScore;

    @Getter
    private Double hydrationScore;

    @Getter
    private Double textureScore;

    @Getter
    private Double sensitivityScore;

    @Getter
    private Double brightnessScore;

    @Getter
    private SkinAnalysisStatus status;

    @Getter
    private LocalDateTime analyzedAt;

    /** Creates a completed skin analysis aggregate from the analyse command using deterministic scoring. */
    public SkinAnalysis(AnalyzeSkinScanCommand command) {
        this.patientId    = new PatientId(command.patientId());
        this.facialScanId = new FacialScanId(command.facialScanId());
        this.skinType     = command.skinType();
        this.status       = SkinAnalysisStatus.COMPLETED;
        this.analyzedAt   = LocalDateTime.now();

        switch (command.skinType()) {
            case "DRY"         -> { this.hydrationScore = 35.0; this.textureScore = 55.0; this.brightnessScore = 60.0; }
            case "OILY"        -> { this.hydrationScore = 75.0; this.textureScore = 50.0; this.brightnessScore = 80.0; }
            case "COMBINATION" -> { this.hydrationScore = 60.0; this.textureScore = 65.0; this.brightnessScore = 70.0; }
            case "SENSITIVE"   -> { this.hydrationScore = 50.0; this.textureScore = 60.0; this.brightnessScore = 55.0; }
            default            -> { this.hydrationScore = 70.0; this.textureScore = 70.0; this.brightnessScore = 75.0; }
        }

        this.sensitivityScore = switch (command.sensitivity()) {
            case "HIGH"   -> 25.0;
            case "MEDIUM" -> 55.0;
            default       -> 80.0;
        };

        this.overallScore = (this.hydrationScore + this.textureScore + this.brightnessScore + this.sensitivityScore) / 4;
    }

    /** Reconstitution constructor — used by the persistence assembler; skips business validation. */
    public SkinAnalysis(Long id, PatientId patientId, FacialScanId facialScanId,
                        String skinType,
                        Double overallScore, Double hydrationScore, Double textureScore,
                        Double sensitivityScore, Double brightnessScore,
                        SkinAnalysisStatus status, LocalDateTime analyzedAt) {
        this.id               = id;
        this.patientId        = patientId;
        this.facialScanId     = facialScanId;
        this.skinType         = skinType;
        this.overallScore     = overallScore;
        this.hydrationScore   = hydrationScore;
        this.textureScore     = textureScore;
        this.sensitivityScore = sensitivityScore;
        this.brightnessScore  = brightnessScore;
        this.status           = status;
        this.analyzedAt       = analyzedAt;
    }

    /** Registers a {@link PreliminaryDiagnosisGeneratedEvent} after a new analysis is persisted. */
    public void onAnalysisCompleted() {
        registerDomainEvent(PreliminaryDiagnosisGeneratedEvent.from(this));
    }

    /** Returns the primitive patient id from the value object. */
    public Long getPatientId() {
        return patientId.patientId();
    }

    /** Returns the primitive facial scan id from the value object. */
    public Long getFacialScanId() {
        return facialScanId.facialScanId();
    }

    /** Returns the full {@link PatientId} value object. */
    public PatientId getPatientIdValue() {
        return patientId;
    }

    /** Returns the full {@link FacialScanId} value object. */
    public FacialScanId getFacialScanIdValue() {
        return facialScanId;
    }
}
