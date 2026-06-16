package com.bloomie.platform.dermatologicalappointment.interfaces.rest.transform;

import com.bloomie.platform.dermatologicalappointment.domain.model.commands.UploadClinicalPhotoCommand;
import com.bloomie.platform.dermatologicalappointment.interfaces.rest.resources.UploadClinicalPhotoResource;

public final class UploadClinicalPhotoCommandFromResourceAssembler {

    private UploadClinicalPhotoCommandFromResourceAssembler() {}

    public static UploadClinicalPhotoCommand toCommandFromResource(Long consultationId,
                                                                    UploadClinicalPhotoResource resource) {
        return new UploadClinicalPhotoCommand(consultationId, resource.photoUrl());
    }
}
