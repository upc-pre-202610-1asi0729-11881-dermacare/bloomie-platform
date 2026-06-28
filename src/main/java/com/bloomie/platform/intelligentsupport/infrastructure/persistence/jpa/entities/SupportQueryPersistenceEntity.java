package com.bloomie.platform.intelligentsupport.infrastructure.persistence.jpa.entities;

import com.bloomie.platform.iam.domain.model.valueobjects.EmailAddress;
import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.PatientId;
import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.SkinProfileId;
import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.SuggestedAction;
import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.SupportQueryStatus;
import com.bloomie.platform.intelligentsupport.infrastructure.persistence.jpa.converters.PatientIdPersistenceConverter;
import com.bloomie.platform.intelligentsupport.infrastructure.persistence.jpa.converters.SkinProfileIdPersistenceConverter;
import com.bloomie.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "support_queries")
public class SupportQueryPersistenceEntity extends AuditableAbstractPersistenceEntity {
    @Convert(converter = PatientIdPersistenceConverter.class)
    @Column(name = "patient_id", nullable = false, unique = true)
    private PatientId patientId;

    @Convert(converter = SkinProfileIdPersistenceConverter.class)
    @Column(name = "skin_profile_id", nullable = false, unique = true)
    private SkinProfileId skinProfileId;

    @Enumerated(EnumType.STRING)
    @Column(name = "support_query_status", nullable = false)
    private SupportQueryStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "suggested_action", nullable = false)
    private SuggestedAction suggestedAction;

}
