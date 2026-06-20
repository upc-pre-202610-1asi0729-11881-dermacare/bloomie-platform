package com.bloomie.platform.dermatologycare.domain.repositories;

import com.bloomie.platform.dermatologycare.domain.model.aggregates.DermatologistProfile;
import com.bloomie.platform.dermatologycare.domain.model.valueobjects.DermatologistId;

import java.util.List;
import java.util.Optional;

public interface DermatologistProfileRepository {
    Optional<DermatologistProfile> findById(Long dermatologistId);
    Optional<DermatologistProfile> findByDermatologistId(DermatologistId dermatologistId);
    List<DermatologistProfile> findAll();
    DermatologistProfile save(DermatologistProfile dermatologistProfile);
    boolean existsByDermatologistId(DermatologistId dermatologistId);
}
