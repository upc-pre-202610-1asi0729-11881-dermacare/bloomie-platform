package com.bloomie.platform.dermatologicalAppointment.interfaces.rest.transform;

import com.bloomie.platform.dermatologicalAppointment.domain.model.commands.UploadClinicalPhotoCommand;
import com.bloomie.platform.dermatologicalAppointment.interfaces.rest.resources.UploadClinicalPhotoResource;

public final class UploadClinicalPhotoCommandFromResourceAssembler {

    private UploadClinicalPhotoCommandFromResourceAssembler() {}

    public static UploadClinicalPhotoCommand toCommandFromResource(Long consultationId,
                                                                    UploadClinicalPhotoResource resource) {
        return new UploadClinicalPhotoCommand(consultationId, resource.photoUrl());
    }
}
