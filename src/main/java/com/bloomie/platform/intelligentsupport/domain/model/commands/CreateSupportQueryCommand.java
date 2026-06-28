package com.bloomie.platform.intelligentsupport.domain.model.commands;

import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.PatientId;
import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.SkinProfileId;
import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.SuggestedAction;
import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.SupportQueryStatus;

import java.time.LocalDateTime;

public record CreateSupportQueryCommand(
        Long patientId,
        Long skinProfileId
) {
}
