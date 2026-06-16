package com.bloomie.platform.dermatologicalAppointment.interfaces.rest.transform;

import com.bloomie.platform.dermatologicalAppointment.domain.model.commands.RecordDermatologicalDiagnosisCommand;
import com.bloomie.platform.dermatologicalAppointment.interfaces.rest.resources.RecordDiagnosisResource;

public final class RecordDiagnosisCommandFromResourceAssembler {

    private RecordDiagnosisCommandFromResourceAssembler() {}

    public static RecordDermatologicalDiagnosisCommand toCommandFromResource(Long consultationId,
                                                                              RecordDiagnosisResource resource) {
        return new RecordDermatologicalDiagnosisCommand(consultationId, resource.notes(), resource.recommendations());
    }
}
