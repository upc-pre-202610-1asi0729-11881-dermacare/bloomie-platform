package com.bloomie.platform.skinAnalysis.infrastructure.persistence.jpa.adapters;

import com.bloomie.platform.skinAnalysis.domain.model.aggregates.SkinProfile;
import com.bloomie.platform.skinAnalysis.domain.model.valueobjects.PatientId;
import com.bloomie.platform.skinAnalysis.domain.repositories.SkinProfileRepository;
import com.bloomie.platform.skinAnalysis.infrastructure.persistence.jpa.assemblers.SkinProfilePersistenceAssembler;
import com.bloomie.platform.skinAnalysis.infrastructure.persistence.jpa.repositories.SkinProfilePersistenceRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA adapter for the {@link SkinProfileRepository} domain port.
 *
 * <p>For <em>new</em> aggregates ({@code id == null}): sets the generated id on the original
 * domain object, calls {@code onCompleted()}, publishes events from the original, clears them,
 * then returns a clean reconstruction from the saved entity.</p>
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
        boolean is_new = skinProfile.getId() == null;
        var saved_entity = persistenceRepository.save(
                SkinProfilePersistenceAssembler.toPersistenceFromDomain(skinProfile));
        if (is_new) {
            skinProfile.setId(saved_entity.getId());
            skinProfile.onCompleted();
        }
        skinProfile.domainEvents().forEach(eventPublisher::publishEvent);
        skinProfile.clearDomainEvents();
        return SkinProfilePersistenceAssembler.toDomainFromPersistence(saved_entity);
    }
}
