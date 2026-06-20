package com.bloomie.platform.dermatologycare.infrastructure.persistence.jpa.adapters;

import com.bloomie.platform.dermatologycare.domain.model.aggregates.DermatologistProfile;
import com.bloomie.platform.dermatologycare.domain.model.valueobjects.DermatologistId;
import com.bloomie.platform.dermatologycare.domain.repositories.DermatologistProfileRepository;
import com.bloomie.platform.dermatologycare.infrastructure.persistence.jpa.assemblers.DermatologistProfilePersistenceAssembler;
import com.bloomie.platform.dermatologycare.infrastructure.persistence.jpa.repositories.DermatologistProfilePersistenceRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter that implements the domain {@link DermatologistProfileRepository} port using JPA.
 *
 * <p>For new profiles (isNew = id == null), calls {@code onRegistered()} on the saved aggregate,
 * publishes the domain event via {@link ApplicationEventPublisher}, and clears it.
 * For updates, the same publish-and-clear cycle fires {@code onUpdated()}.</p>
 */
@Repository
public class DermatologistProfileRepositoryImpl implements DermatologistProfileRepository {

    private final DermatologistProfilePersistenceRepository persistenceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public DermatologistProfileRepositoryImpl(DermatologistProfilePersistenceRepository persistenceRepository,
                                              ApplicationEventPublisher eventPublisher) {
        this.persistenceRepository = persistenceRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<DermatologistProfile> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(DermatologistProfilePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<DermatologistProfile> findByDermatologistId(DermatologistId dermatologistId) {
        return persistenceRepository.findByDermatologistId(dermatologistId)
                .map(DermatologistProfilePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<DermatologistProfile> findAll() {
        return persistenceRepository.findAll().stream()
                .map(DermatologistProfilePersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public DermatologistProfile save(DermatologistProfile profile) {
        boolean isNew = profile.getId() == null;
        var savedEntity = persistenceRepository.save(
                DermatologistProfilePersistenceAssembler.toPersistenceFromDomain(profile));
        var savedProfile = DermatologistProfilePersistenceAssembler.toDomainFromPersistence(savedEntity);
        if (isNew) {
            savedProfile.onRegistered();
        } else {
            savedProfile.onUpdated();
        }
        savedProfile.domainEvents().forEach(eventPublisher::publishEvent);
        savedProfile.clearDomainEvents();
        return savedProfile;
    }

    @Override
    public boolean existsByDermatologistId(DermatologistId dermatologistId) {
        return persistenceRepository.countByDermatologistId(dermatologistId) > 0;
    }
}
