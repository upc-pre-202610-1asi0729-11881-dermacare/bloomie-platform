package com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.adapters;

import com.bloomie.platform.routinemanagement.domain.model.aggregates.DailyTracking;
import com.bloomie.platform.routinemanagement.domain.model.valueobjects.PatientId;
import com.bloomie.platform.routinemanagement.domain.model.valueobjects.RoutineId;
import com.bloomie.platform.routinemanagement.domain.repositories.DailyTrackingRepository;
import com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.assemblers.DailyTrackingPersistenceAssembler;
import com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.repositories.DailyTrackingPersistenceRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * JPA adapter for the {@link DailyTrackingRepository} domain port.
 *
 * <p>For <em>new</em> aggregates ({@code id == null}): saves the entity, then calls
 * {@code onCompleted()} on the reconstructed aggregate, publishes domain events and clears them.</p>
 */
@Repository
public class DailyTrackingRepositoryImpl implements DailyTrackingRepository {

    private final DailyTrackingPersistenceRepository dailyTrackingPersistenceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public DailyTrackingRepositoryImpl(DailyTrackingPersistenceRepository dailyTrackingPersistenceRepository,
                                       ApplicationEventPublisher eventPublisher) {
        this.dailyTrackingPersistenceRepository = dailyTrackingPersistenceRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<DailyTracking> findById(Long id) {
        return dailyTrackingPersistenceRepository.findById(id)
                .map(DailyTrackingPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<DailyTracking> findByPatientIdAndDate(PatientId patientId, LocalDate date) {
        return dailyTrackingPersistenceRepository.findByPatientIdAndDate(patientId, date)
                .map(DailyTrackingPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<DailyTracking> findAllByPatientId(PatientId patientId) {
        return dailyTrackingPersistenceRepository.findAllByPatientId(patientId).stream()
                .map(DailyTrackingPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<DailyTracking> findAllByRoutineId(RoutineId routineId) {
        return dailyTrackingPersistenceRepository.findAllByRoutineId(routineId).stream()
                .map(DailyTrackingPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<DailyTracking> findAll() {
        return dailyTrackingPersistenceRepository.findAll().stream()
                .map(DailyTrackingPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public DailyTracking save(DailyTracking dailyTracking) {
        boolean isNew = dailyTracking.getId() == null;
        var entity = DailyTrackingPersistenceAssembler.toPersistenceFromDomain(dailyTracking);
        var savedEntity = dailyTrackingPersistenceRepository.save(entity);
        var savedTracking = DailyTrackingPersistenceAssembler.toDomainFromPersistence(savedEntity);
        if (isNew) {
            savedTracking.onCompleted();
            savedTracking.domainEvents().forEach(eventPublisher::publishEvent);
            savedTracking.clearDomainEvents();
        }
        return savedTracking;
    }

    @Override
    public boolean existsByPatientIdAndDate(PatientId patientId, LocalDate date) {
        return dailyTrackingPersistenceRepository.existsByPatientIdAndDate(patientId, date);
    }
}
