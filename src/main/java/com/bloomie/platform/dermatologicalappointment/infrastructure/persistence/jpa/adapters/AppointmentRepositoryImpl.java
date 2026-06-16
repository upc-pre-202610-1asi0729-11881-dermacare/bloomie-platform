package com.bloomie.platform.dermatologicalAppointment.infrastructure.persistence.jpa.adapters;

import com.bloomie.platform.dermatologicalAppointment.domain.model.aggregates.Appointment;
import com.bloomie.platform.dermatologicalAppointment.domain.model.valueobjects.AppointmentDateTime;
import com.bloomie.platform.dermatologicalAppointment.domain.model.valueobjects.AppointmentStatus;
import com.bloomie.platform.dermatologicalAppointment.domain.model.valueobjects.DermatologistId;
import com.bloomie.platform.dermatologicalAppointment.domain.model.valueobjects.PatientId;
import com.bloomie.platform.dermatologicalAppointment.domain.repositories.AppointmentRepository;
import com.bloomie.platform.dermatologicalAppointment.infrastructure.persistence.jpa.assemblers.AppointmentPersistenceAssembler;
import com.bloomie.platform.dermatologicalAppointment.infrastructure.persistence.jpa.repositories.AppointmentPersistenceRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA adapter for the {@link AppointmentRepository} domain port.
 *
 * <p>For <em>new</em> aggregates ({@code id == null}): sets the generated id on the original
 * domain object, calls {@code onScheduled()}, publishes events from the original, clears them,
 * then returns a clean reconstruction. For <em>updates</em>: the application service has already
 * called {@code onXxx()} on the original; this adapter publishes those events and returns a
 * clean reconstruction.</p>
 */
@Repository
public class AppointmentRepositoryImpl implements AppointmentRepository {

    private final AppointmentPersistenceRepository persistenceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public AppointmentRepositoryImpl(AppointmentPersistenceRepository persistenceRepository,
                                     ApplicationEventPublisher eventPublisher) {
        this.persistenceRepository = persistenceRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(AppointmentPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Appointment> findByPatientId(PatientId patientId) {
        return persistenceRepository.findAllByPatientId(patientId).stream()
                .map(AppointmentPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<Appointment> findByDermatologistId(DermatologistId dermatologistId) {
        return persistenceRepository.findAllByDermatologistId(dermatologistId).stream()
                .map(AppointmentPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Appointment save(Appointment appointment) {
        boolean isNew = appointment.getId() == null;
        var savedEntity = persistenceRepository.save(
                AppointmentPersistenceAssembler.toPersistenceFromDomain(appointment));
        if (isNew) {
            appointment.setId(savedEntity.getId());
            appointment.onScheduled();
        }
        appointment.domainEvents().forEach(eventPublisher::publishEvent);
        appointment.clearDomainEvents();
        return AppointmentPersistenceAssembler.toDomainFromPersistence(savedEntity);
    }

    @Override
    public boolean existsById(Long id) {
        return persistenceRepository.existsById(id);
    }

    @Override
    public boolean existsByDermatologistIdAndDate(DermatologistId dermatologistId, AppointmentDateTime scheduledAt) {
        return persistenceRepository.existsByDermatologistIdAndScheduledAtAndStatusNot(
                dermatologistId, scheduledAt, AppointmentStatus.CANCELLED);
    }
}
