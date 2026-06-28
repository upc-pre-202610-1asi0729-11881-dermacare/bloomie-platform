package com.bloomie.platform.intelligentsupport.interfaces.rest.transform;

import com.bloomie.platform.intelligentsupport.domain.model.commands.UpdateSupportQueryStatusCommand;
import com.bloomie.platform.intelligentsupport.interfaces.rest.resources.UpdateSupportQueryStatusResource;

public final class UpdateSupportQueryStatusCommandFromResourceAssembler {

    private UpdateSupportQueryStatusCommandFromResourceAssembler() {}

    public static UpdateSupportQueryStatusCommand toCommandFromResource(
            Long supportQueryId, UpdateSupportQueryStatusResource resource) {
        return new UpdateSupportQueryStatusCommand(
                supportQueryId,
                resource.status()
        );
    }
}