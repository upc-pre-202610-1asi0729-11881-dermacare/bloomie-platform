package com.bloomie.platform.skinanalysis.infrastructure.persistence.jpa.adapters;

import com.bloomie.platform.skinanalysis.domain.model.aggregates.SkinAnalysis;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.FacialScanId;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.PatientId;
import com.bloomie.platform.skinanalysis.domain.repositories.SkinAnalysisRepository;
import com.bloomie.platform.skinanalysis.infrastructure.persistence.jpa.assemblers.SkinAnalysisPersistenceAssembler;
import com.bloomie.platform.skinanalysis.infrastructure.persistence.jpa.repositories.SkinAnalysisPersistenceRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA adapter for the {@link SkinAnalysisRepository} domain port.
 *
 * <p>For <em>new</em> aggregates ({@code id == null}): saves the entity, then calls
 * {@code onAnalysisCompleted()} on the reconstructed aggregate, publishes events and clears them.</p>
 */
@Repository
public class SkinAnalysisRepositoryImpl implements SkinAnalysisRepository {

    private final SkinAnalysisPersistenceRepository persistenceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public SkinAnalysisRepositoryImpl(SkinAnalysisPersistenceRepository persistenceRepository,
                                      ApplicationEventPublisher eventPublisher) {
        this.persistenceRepository = persistenceRepository;
        this.eventPublisher        = eventPublisher;
    }

    @Override
    public Optional<SkinAnalysis> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(SkinAnalysisPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<SkinAnalysis> findByFacialScanId(FacialScanId facialScanId) {
        return persistenceRepository.findByFacialScanId(facialScanId)
                .map(SkinAnalysisPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<SkinAnalysis> findAllByPatientId(PatientId patientId) {
        return persistenceRepository.findAllByPatientId(patientId).stream()
                .map(SkinAnalysisPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public SkinAnalysis save(SkinAnalysis skinAnalysis) {
        boolean isNew = skinAnalysis.getId() == null;
        var savedEntity = persistenceRepository.save(
                SkinAnalysisPersistenceAssembler.toPersistenceFromDomain(skinAnalysis));
        var savedAnalysis = SkinAnalysisPersistenceAssembler.toDomainFromPersistence(savedEntity);
        if (isNew) {
            savedAnalysis.onAnalysisCompleted();
        }
        savedAnalysis.domainEvents().forEach(eventPublisher::publishEvent);
        savedAnalysis.clearDomainEvents();
        return savedAnalysis;
    }
}
