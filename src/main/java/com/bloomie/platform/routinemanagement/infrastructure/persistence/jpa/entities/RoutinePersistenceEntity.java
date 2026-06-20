package com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.entities;

import com.bloomie.platform.routinemanagement.domain.model.valueobjects.PatientId;
import com.bloomie.platform.routinemanagement.domain.model.valueobjects.RoutineStatus;
import com.bloomie.platform.routinemanagement.domain.model.valueobjects.SkinAnalysisId;
import com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.converters.PatientIdPersistenceConverter;
import com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.converters.SkinAnalysisIdPersistenceConverter;
import com.bloomie.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * JPA persistence entity for routines.
 */
@Table(name = "routines")
@Getter
@Setter
@NoArgsConstructor
@NamedEntityGraph(
        name = "Routine.withItems",
        attributeNodes = @NamedAttributeNode("items")
)
@Entity
public class RoutinePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = PatientIdPersistenceConverter.class)
    @Column(name = "patient_id", nullable = false)
    private PatientId patientId;

    @Convert(converter = SkinAnalysisIdPersistenceConverter.class)
    @Column(name = "skin_analysis_id", nullable = false)
    private SkinAnalysisId skinAnalysisId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoutineStatus status;

    @Column(name = "skin_type", nullable = false)
    private String skinType;

    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<RoutineItemPersistenceEntity> items = new ArrayList<>();
}