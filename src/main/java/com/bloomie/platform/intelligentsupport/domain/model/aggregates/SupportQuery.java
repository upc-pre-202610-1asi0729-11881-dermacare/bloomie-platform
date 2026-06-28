package com.bloomie.platform.intelligentsupport.domain.model.aggregates;

import com.bloomie.platform.intelligentsupport.domain.model.commands.CreateSupportQueryCommand;
import com.bloomie.platform.intelligentsupport.domain.model.events.SupportQueryCreatedEvent;
import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.*;
import com.bloomie.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SupportQuery extends AbstractDomainAggregateRoot<SupportQuery> {
    private Long id;
    private PatientId patientId;
    private SkinProfileId skinProfileId;
    private SupportQueryStatus status;
    private SuggestedAction suggestedAction;
    private LocalDateTime createdAt;

    public SupportQuery(Long id, PatientId patientId, SkinProfileId skinProfileId, SupportQueryStatus status, SuggestedAction suggestedAction, LocalDateTime createdAt) {
        this.id = id;
        this.patientId = patientId;
        this.skinProfileId = skinProfileId;
        this.status = status;
        this.suggestedAction = suggestedAction;
        this.createdAt = createdAt;
    }

    public SupportQuery(PatientId patientId, SkinProfileId skinProfileId){
        this(
                null,
                patientId,
                skinProfileId,
                SupportQueryStatus.IN_PROGRESS,
                SuggestedAction.CONTINUE_ROUTINE,
                LocalDateTime.now()
        );
    }

    public SupportQuery(CreateSupportQueryCommand command) {
        this(
               new PatientId(command.patientId()),
               new SkinProfileId(command.skinProfileId())
        );
    }

    public void onCreated() {
        registerDomainEvent(SupportQueryCreatedEvent.from(this));
    }
}
