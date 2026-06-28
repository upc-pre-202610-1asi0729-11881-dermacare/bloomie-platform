package com.bloomie.platform.intelligentsupport.infrastructure.persistence.jpa.adapters;

import com.bloomie.platform.intelligentsupport.domain.model.aggregates.SupportQuery;
import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.PatientId;
import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.SupportQueryStatus;
import com.bloomie.platform.intelligentsupport.domain.repositories.SupportQueryRepository;
import com.bloomie.platform.intelligentsupport.infrastructure.persistence.jpa.assemblers.SupportQueryPersistenceAssembler;
import com.bloomie.platform.intelligentsupport.infrastructure.persistence.jpa.repositories.SupportQueryPersistenceRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SupportQueryRepositoryImpl implements SupportQueryRepository {

    private final SupportQueryPersistenceRepository persistenceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public SupportQueryRepositoryImpl(SupportQueryPersistenceRepository persistenceRepository,
                                      ApplicationEventPublisher eventPublisher) {
        this.persistenceRepository = persistenceRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<SupportQuery> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(SupportQueryPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<SupportQuery> findByPatientIdAndStatus(PatientId patientId, SupportQueryStatus status) {
        return persistenceRepository.findByPatientIdAndStatus(patientId, status)
                .map(SupportQueryPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<SupportQuery> findAllByPatientId(PatientId patientId) {
        return persistenceRepository.findAllByPatientId(patientId).stream()
                .map(SupportQueryPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public SupportQuery save(SupportQuery supportQuery) {
        boolean isNew = supportQuery.getId() == null;
        var savedEntity = persistenceRepository.save(
                SupportQueryPersistenceAssembler.toPersistenceFromDomain(supportQuery));
        var savedQuery = SupportQueryPersistenceAssembler.toDomainFromPersistence(savedEntity);
        if (isNew) {
            savedQuery.onCreated();
        }
        savedQuery.domainEvents().forEach(eventPublisher::publishEvent);
        savedQuery.clearDomainEvents();
        return savedQuery;
    }
}