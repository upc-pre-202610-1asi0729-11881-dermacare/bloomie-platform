package com.bloomie.platform.payments.infrastructure.persistence.jpa.adapters;

import com.bloomie.platform.payments.domain.model.aggregates.Payment;
import com.bloomie.platform.payments.domain.model.events.SubscriptionPaymentProcessedEvent;
import com.bloomie.platform.payments.domain.model.valueobjects.PatientId;
import com.bloomie.platform.payments.domain.model.valueobjects.PaymentType;
import com.bloomie.platform.payments.domain.model.valueobjects.SubscriptionId;
import com.bloomie.platform.payments.domain.repositories.PaymentRepository;
import com.bloomie.platform.payments.infrastructure.persistence.jpa.assemblers.PaymentPersistenceAssembler;
import com.bloomie.platform.payments.infrastructure.persistence.jpa.repositories.PaymentPersistenceRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository adapter that bridges the domain payment repository port with Spring Data JPA.
 *
 * <p>Also acts as the event-publishing boundary: after a brand-new {@link Payment} is
 * persisted (and the JPA-assigned id is therefore available), a {@link SubscriptionPaymentProcessedEvent}
 * is dispatched via Spring's {@link ApplicationEventPublisher}.</p>
 */
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
        // isRefunding is read from the original aggregate before it is replaced by
        // the reconstructed savedPayment, which always starts with refunding = false.
        boolean isRefunding = !isNew && payment.isRefunding();
        var savedEntity = paymentPersistenceRepository.save(PaymentPersistenceAssembler.toPersistenceFromDomain(payment));
        var savedPayment = PaymentPersistenceAssembler.toDomainFromPersistence(savedEntity);
        if (isNew) {
            // Each payment type fires a different domain event so consumers can
            // react appropriately (activate vs. renew the subscription).
            if (savedPayment.getType() == PaymentType.RENEWAL) {
                savedPayment.onProcessRenewalPayment();
            } else {
                savedPayment.onProcessSubscriptionPayment();
            }
            // Persist the PENDING → PROCESSED status transition.
            paymentPersistenceRepository.save(PaymentPersistenceAssembler.toPersistenceFromDomain(savedPayment));
        } else if (isRefunding) {
            savedPayment.onRefunded();
        }
        savedPayment.domainEvents().forEach(eventPublisher::publishEvent);
        savedPayment.clearDomainEvents();
        return savedPayment;
    }

    @Override
    public List<Payment> findAllByPatientId(PatientId id) {
        return paymentPersistenceRepository.findAllByPatientId(id).stream().map(PaymentPersistenceAssembler::toDomainFromPersistence).toList();
    }

    @Override
    public Optional<Payment> findBySubscriptionId(SubscriptionId id) {
        return paymentPersistenceRepository.findBySubscriptionId(id).map(PaymentPersistenceAssembler::toDomainFromPersistence);
    }
}
