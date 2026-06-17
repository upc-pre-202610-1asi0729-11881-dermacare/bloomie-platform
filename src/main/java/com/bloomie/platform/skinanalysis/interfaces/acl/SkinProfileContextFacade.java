package com.bloomie.platform.skinanalysis.interfaces.acl;

/**
 * Published language of the Skin Analysis context — exposes skin profile data to other
 * bounded contexts using primitive types only.
 *
 * <p>Consumers must depend on this interface, never on internal domain objects.</p>
 */
public interface SkinProfileContextFacade {

    /**
     * Returns the skin type name for the patient with the given id.
     *
     * @param patientId the IAM user id of the patient
     * @return the skin type name, or {@code null} if no profile exists
     */
    String fetchSkinTypeByPatientId(Long patientId);

    /**
     * Returns the sensitivity name for the patient with the given id.
     *
     * @param patientId the IAM user id of the patient
     * @return the sensitivity name, or {@code null} if no profile exists
     */
    String fetchSensitivityByPatientId(Long patientId);
}
