package com.bloomie.platform.dermatologicalappointment.infrastructure.persistence.jpa.adapters;

import com.bloomie.platform.dermatologicalappointment.domain.model.aggregates.Consultation;
import com.bloomie.platform.dermatologicalappointment.domain.repositories.ConsultationRepository;
import com.bloomie.platform.dermatologicalappointment.infrastructure.persistence.jpa.assemblers.ConsultationPersistenceAssembler;
import com.bloomie.platform.dermatologicalappointment.infrastructure.persistence.jpa.repositories.ConsultationPersistenceRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA adapter for the {@link ConsultationRepository} domain port.
 *
 * <p>For <em>new</em> consultations ({@code id == null}): sets the generated id on the original
 * domain object, calls {@code onStarted()}, publishes events, clears them, then returns a clean
 * reconstruction. For <em>updates</em>: publishes events already registered by the application
 * service and returns a clean reconstruction.</p>
 */
@Repository
public class ConsultationRepositoryImpl implements ConsultationRepository {

    private final ConsultationPersistenceRepository persistenceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ConsultationRepositoryImpl(ConsultationPersistenceRepository persistenceRepository,
                                      ApplicationEventPublisher eventPublisher) {
        this.persistenceRepository = persistenceRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<Consultation> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(ConsultationPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<Consultation> findByAppointmentId(Long appointmentId) {
        return persistenceRepository.findByAppointmentId(appointmentId)
                .map(ConsultationPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Consultation save(Consultation consultation) {
        boolean isNew = consultation.getId() == null;
        var savedEntity = persistenceRepository.save(
                ConsultationPersistenceAssembler.toPersistenceFromDomain(consultation));
        if (isNew) {
            consultation.setId(savedEntity.getId());
            consultation.onStarted();
        }
        consultation.domainEvents().forEach(eventPublisher::publishEvent);
        consultation.clearDomainEvents();
        return ConsultationPersistenceAssembler.toDomainFromPersistence(savedEntity);
    }

    @Override
    public List<Consultation> findAll() {
        return persistenceRepository.findAll().stream()
                .map(ConsultationPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }
}
