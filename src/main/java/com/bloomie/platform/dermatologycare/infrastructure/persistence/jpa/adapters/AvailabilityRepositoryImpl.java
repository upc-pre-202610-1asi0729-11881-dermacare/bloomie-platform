package com.bloomie.platform.dermatologycare.infrastructure.persistence.jpa.adapters;

import com.bloomie.platform.dermatologycare.domain.model.aggregates.Availability;
import com.bloomie.platform.dermatologycare.domain.model.valueobjects.DermatologistId;
import com.bloomie.platform.dermatologycare.domain.repositories.AvailabilityRepository;
import com.bloomie.platform.dermatologycare.infrastructure.persistence.jpa.assemblers.AvailabilityPersistenceAssembler;
import com.bloomie.platform.dermatologycare.infrastructure.persistence.jpa.repositories.AvailabilityPersistenceRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

/**
 * Adapter that implements the domain {@link AvailabilityRepository} port using JPA.
 *
 * <p>Publishes domain events via {@link ApplicationEventPublisher} after every save:
 * {@code onDefined()} for new slots, {@code onUpdated()} for existing ones.</p>
 */
@Repository
public class AvailabilityRepositoryImpl implements AvailabilityRepository {

    private final AvailabilityPersistenceRepository persistenceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public AvailabilityRepositoryImpl(AvailabilityPersistenceRepository persistenceRepository,
                                      ApplicationEventPublisher eventPublisher) {
        this.persistenceRepository = persistenceRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<Availability> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(AvailabilityPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Availability> findAllByDermatologistId(DermatologistId dermatologistId) {
        return persistenceRepository.findAllByDermatologistId(dermatologistId).stream()
                .map(AvailabilityPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<Availability> findAllByDermatologistAndDay(DermatologistId dermatologistId, DayOfWeek day) {
        return persistenceRepository.findAllByDermatologistIdAndDay(dermatologistId, day).stream()
                .map(AvailabilityPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Availability save(Availability availability) {
        boolean isNew = availability.getId() == null;
        var savedEntity = persistenceRepository.save(
                AvailabilityPersistenceAssembler.toPersistenceFromDomain(availability));
        var savedAvailability = AvailabilityPersistenceAssembler.toDomainFromPersistence(savedEntity);
        if (isNew) {
            savedAvailability.onDefined();
        } else {
            savedAvailability.onUpdated();
        }
        savedAvailability.domainEvents().forEach(eventPublisher::publishEvent);
        savedAvailability.clearDomainEvents();
        return savedAvailability;
    }

    @Override
    public boolean existsByDermatologyAndDay(DermatologistId dermatologistId, DayOfWeek day) {
        return persistenceRepository.countByDermatologistIdAndDay(dermatologistId, day) > 0;
    }
}
