package com.bloomie.platform.dermatologicalAppointment.interfaces.rest.transform;

import com.bloomie.platform.dermatologicalAppointment.domain.model.commands.SaveNotesCommand;
import com.bloomie.platform.dermatologicalAppointment.interfaces.rest.resources.SaveNotesResource;

public final class SaveNotesCommandFromResourceAssembler {

    private SaveNotesCommandFromResourceAssembler() {}

    public static SaveNotesCommand toCommandFromResource(Long consultationId, SaveNotesResource resource) {
        return new SaveNotesCommand(consultationId, resource.notes());
    }
}
