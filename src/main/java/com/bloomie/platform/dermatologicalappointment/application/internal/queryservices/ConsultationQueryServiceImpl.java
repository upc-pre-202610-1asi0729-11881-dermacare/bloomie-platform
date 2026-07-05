package com.bloomie.platform.dermatologicalappointment.application.internal.queryservices;

import com.bloomie.platform.dermatologicalappointment.application.queryservices.ConsultationQueryService;
import com.bloomie.platform.dermatologicalappointment.domain.model.aggregates.Consultation;
import com.bloomie.platform.dermatologicalappointment.domain.model.queries.GetConsultationByAppointmentIdQuery;
import com.bloomie.platform.dermatologicalappointment.domain.model.queries.GetConsultationByIdQuery;
import com.bloomie.platform.dermatologicalappointment.domain.repositories.ConsultationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service that handles all read operations on the {@link Consultation} aggregate.
 *
 * <p>Delegates directly to the {@link ConsultationRepository} domain port without any
 * mutation or event publication.</p>
 */
@Service
public class ConsultationQueryServiceImpl implements ConsultationQueryService {

    private final ConsultationRepository consultationRepository;

    public ConsultationQueryServiceImpl(ConsultationRepository consultationRepository) {
        this.consultationRepository = consultationRepository;
    }

    @Override
    public Optional<Consultation> handle(GetConsultationByIdQuery query) {
        return consultationRepository.findById(query.consultationId());
    }

    @Override
    public Optional<Consultation> handle(GetConsultationByAppointmentIdQuery query) {
        return consultationRepository.findByAppointmentId(query.appointmentId());
    }

    @Override
    public List<Consultation> handleGetAll() {
        return consultationRepository.findAll();
    }
}
