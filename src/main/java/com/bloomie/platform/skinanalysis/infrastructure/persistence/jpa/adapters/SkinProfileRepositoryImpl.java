package com.bloomie.platform.skinanalysis.infrastructure.persistence.jpa.adapters;

import com.bloomie.platform.skinanalysis.domain.model.aggregates.SkinProfile;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.PatientId;
import com.bloomie.platform.skinanalysis.domain.repositories.SkinProfileRepository;
import com.bloomie.platform.skinanalysis.infrastructure.persistence.jpa.assemblers.SkinProfilePersistenceAssembler;
import com.bloomie.platform.skinanalysis.infrastructure.persistence.jpa.repositories.SkinProfilePersistenceRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA adapter for the {@link SkinProfileRepository} domain port.
 *
 * <p>For <em>new</em> aggregates ({@code id == null}): saves the entity, then calls
 * {@code onCompleted()} on the reconstructed aggregate, publishes events and clears them.
 * For <em>updates</em>: calls {@code onUpdated()} on the reconstructed aggregate,
 * publishes events and clears them.</p>
 */
@Repository
public class SkinProfileRepositoryImpl implements SkinProfileRepository {

    private final SkinProfilePersistenceRepository persistenceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public SkinProfileRepositoryImpl(SkinProfilePersistenceRepository persistenceRepository,
                                     ApplicationEventPublisher eventPublisher) {
        this.persistenceRepository = persistenceRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<SkinProfile> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(SkinProfilePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<SkinProfile> findByPatientId(PatientId patientId) {
        return persistenceRepository.findByPatientId(patientId)
                .map(SkinProfilePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public boolean existsByPatientId(PatientId patientId) {
        return persistenceRepository.existsByPatientId(patientId);
    }

    @Override
    public SkinProfile save(SkinProfile skinProfile) {
        boolean isNew = skinProfile.getId() == null;
        var savedEntity = persistenceRepository.save(
                SkinProfilePersistenceAssembler.toPersistenceFromDomain(skinProfile));
        var savedProfile = SkinProfilePersistenceAssembler.toDomainFromPersistence(savedEntity);
        if (isNew) {
            savedProfile.onCompleted();
        } else {
            savedProfile.onUpdated();
        }
        savedProfile.domainEvents().forEach(eventPublisher::publishEvent);
        savedProfile.clearDomainEvents();
        return savedProfile;
    }
}
