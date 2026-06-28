package com.bloomie.platform.intelligentsupport.application.internal.commandservices;

import com.bloomie.platform.intelligentsupport.application.commandservices.SupportQueryCommandService;
import com.bloomie.platform.intelligentsupport.domain.model.aggregates.SupportQuery;
import com.bloomie.platform.intelligentsupport.domain.model.commands.CreateSupportQueryCommand;
import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.PatientId;
import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.SupportQueryStatus;
import com.bloomie.platform.intelligentsupport.domain.repositories.SupportQueryRepository;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class SupportQueryCommandServiceImpl implements SupportQueryCommandService {
    private final SupportQueryRepository supportQueryRepository;

    public SupportQueryCommandServiceImpl(SupportQueryRepository supportQueryRepository) {
        this.supportQueryRepository = supportQueryRepository;
    }

    @Override
    public Result<SupportQuery, ApplicationError> handle(CreateSupportQueryCommand command) {
        try {
            var existing = supportQueryRepository.findByPatientIdAndStatus(new PatientId(command.patientId()), SupportQueryStatus.IN_PROGRESS);
            existing.ifPresent(supportQuery -> {
                supportQuery.setStatus(SupportQueryStatus.UNRESOLVED);
                supportQueryRepository.save(supportQuery);
            });


            var newQuery = new SupportQuery(command);
            var savedNewQuery = supportQueryRepository.save(newQuery);
            return Result.success(savedNewQuery);
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("Support Query", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "Support query creation",
                    e.getMessage()));
        }
    }
}
