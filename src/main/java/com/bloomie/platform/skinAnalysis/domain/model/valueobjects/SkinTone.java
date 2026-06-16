package com.bloomie.platform.skinAnalysis.domain.model.valueobjects;

/**
 * Skin tone classification used to personalise product recommendations.
 *
 * <ul>
 *   <li>{@code FAIR}   – very light skin, burns easily.</li>
 *   <li>{@code LIGHT}  – light skin, sometimes tans.</li>
 *   <li>{@code MEDIUM} – medium skin, tans moderately.</li>
 *   <li>{@code OLIVE}  – olive / tan skin, rarely burns.</li>
 *   <li>{@code DARK}   – dark skin, very rarely burns.</li>
 *   <li>{@code DEEP}   – deeply pigmented skin, never burns.</li>
 * </ul>
 */
public enum SkinTone {
    FAIR,
    LIGHT,
    MEDIUM,
    OLIVE,
    DARK,
    DEEP
}
