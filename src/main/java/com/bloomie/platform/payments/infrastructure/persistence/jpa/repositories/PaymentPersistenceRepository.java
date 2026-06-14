package com.bloomie.platform.payments.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentPersistenceRepository extends JpaRepository<PaymentPersistenceRepository, Long> {

}
