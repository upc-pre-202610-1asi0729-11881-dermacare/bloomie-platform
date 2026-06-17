package com.bloomie.platform.skinanalysis.domain.model.aggregates;

import com.bloomie.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.bloomie.platform.skinanalysis.domain.model.commands.StartFacialScanCommand;
import com.bloomie.platform.skinanalysis.domain.model.events.FacialScanStartedEvent;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.FacialScanStatus;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.PatientId;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Aggregate root representing a patient's facial scan session.
 *
 * <p>Created in {@code STARTED} status when the patient initiates a scan via
 * {@link StartFacialScanCommand}. The photo URL and status are updated as the
 * scan progresses through its lifecycle.</p>
 *
 * <p>Domain events are published by the repository adapter after each save.</p>
 */
public class FacialScan extends AbstractDomainAggregateRoot<FacialScan> {

    @Getter
    @Setter
    private Long id;

    private PatientId patientId;

    @Getter
    private FacialScanStatus status;

    @Getter
    private String photoUrl;

    @Getter
    private LocalDateTime scannedAt;

    /** Creates a new facial scan session from the patient's start command. */
    public FacialScan(StartFacialScanCommand command) {
        this.patientId = new PatientId(command.patientId());
        this.status = FacialScanStatus.STARTED;
        this.scannedAt = LocalDateTime.now();
        this.photoUrl = null;
    }

    /** Reconstitution constructor — used by the persistence assembler; skips business validation. */
    public FacialScan(Long id, PatientId patientId, FacialScanStatus status,
                      String photoUrl, LocalDateTime scannedAt) {
        this.id = id;
        this.patientId = patientId;
        this.status = status;
        this.photoUrl = photoUrl;
        this.scannedAt = scannedAt;
    }

    /** Registers a {@link FacialScanStartedEvent} after a new scan is persisted. */
    public void onStarted() {
        registerDomainEvent(FacialScanStartedEvent.from(this));
    }

    /** Returns the primitive patient id from the value object. */
    public Long getPatientId() {
        return patientId.patientId();
    }

    /** Returns the full {@link PatientId} value object. */
    public PatientId getPatientIdValue() {
        return patientId;
    }
}
