package com.bloomie.platform.skinanalysis.infrastructure.persistence.jpa.adapters;

import com.bloomie.platform.skinanalysis.domain.model.aggregates.FacialScan;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.PatientId;
import com.bloomie.platform.skinanalysis.domain.repositories.FacialScanRepository;
import com.bloomie.platform.skinanalysis.infrastructure.persistence.jpa.assemblers.FacialScanPersistenceAssembler;
import com.bloomie.platform.skinanalysis.infrastructure.persistence.jpa.repositories.FacialScanPersistenceRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA adapter for the {@link FacialScanRepository} domain port.
 *
 * <p>For <em>new</em> aggregates ({@code id == null}): saves the entity, then calls
 * {@code onStarted()} on the reconstructed aggregate, publishes events and clears them.</p>
 */
@Repository
public class FacialScanRepositoryImpl implements FacialScanRepository {

    private final FacialScanPersistenceRepository persistenceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public FacialScanRepositoryImpl(FacialScanPersistenceRepository persistenceRepository,
                                    ApplicationEventPublisher eventPublisher) {
        this.persistenceRepository = persistenceRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<FacialScan> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(FacialScanPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<FacialScan> findAllByPatientId(PatientId patientId) {
        return persistenceRepository.findAllByPatientId(patientId).stream()
                .map(FacialScanPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public FacialScan save(FacialScan facialScan) {
        boolean isNew = facialScan.getId() == null;
        var savedEntity = persistenceRepository.save(
                FacialScanPersistenceAssembler.toPersistenceFromDomain(facialScan));
        var savedScan = FacialScanPersistenceAssembler.toDomainFromPersistence(savedEntity);
        if (isNew) {
            savedScan.onStarted();
        }
        savedScan.domainEvents().forEach(eventPublisher::publishEvent);
        savedScan.clearDomainEvents();
        return savedScan;
    }
}
