package com.bloomie.platform.payments.infrastructure.persistence.jpa.adapters;

import com.bloomie.platform.payments.domain.model.aggregates.Payment;
import com.bloomie.platform.payments.domain.model.valueobjects.PatientId;
import com.bloomie.platform.payments.domain.repositories.PaymentRepository;
import com.bloomie.platform.payments.infrastructure.persistence.jpa.assemblers.PaymentPersistenceAssembler;
import com.bloomie.platform.payments.infrastructure.persistence.jpa.entities.PaymentPersistenceEntity;
import com.bloomie.platform.payments.infrastructure.persistence.jpa.repositories.PaymentPersistenceRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {
    private final PaymentPersistenceRepository paymentPersistenceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public PaymentRepositoryImpl(PaymentPersistenceRepository paymentPersistenceRepository, ApplicationEventPublisher eventPublisher) {
        this.paymentPersistenceRepository = paymentPersistenceRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return paymentPersistenceRepository.findById(id).map(PaymentPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Payment save(Payment payment) {
        boolean isNew = payment.getId() == null;
        var savedEntity = paymentPersistenceRepository.save(PaymentPersistenceAssembler.toPersistenceFromDomain(payment));
        var savedPayment  = PaymentPersistenceAssembler.toDomainFromPersistence(savedEntity);
        if (isNew) {
            savedPayment .onProcessSubscriptionPayment();
            savedPayment .domainEvents().forEach(eventPublisher::publishEvent);
            savedPayment .clearDomainEvents();
        }
        return savedPayment ;
    }

    @Override
    public List<Payment> findAllByPatientId(PatientId id) {
        return paymentPersistenceRepository.findAllByPatientId(id).stream().map(PaymentPersistenceAssembler::toDomainFromPersistence).toList();
    }
}
