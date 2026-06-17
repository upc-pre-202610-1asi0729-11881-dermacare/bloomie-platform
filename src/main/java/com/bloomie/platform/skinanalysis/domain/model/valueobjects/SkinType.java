package com.bloomie.platform.skinanalysis.domain.model.valueobjects;

/**
 * Skin type classification.
 *
 * <ul>
 *   <li>{@code NORMAL}      – balanced, no major concerns.</li>
 *   <li>{@code DRY}         – lacks moisture and lipids.</li>
 *   <li>{@code OILY}        – excess sebum production.</li>
 *   <li>{@code COMBINATION} – oily in T-zone, dry on cheeks.</li>
 *   <li>{@code SENSITIVE}   – prone to irritation and reactions.</li>
 * </ul>
 */
public enum SkinType {
    NORMAL,
    DRY,
    OILY,
    COMBINATION,
    SENSITIVE
}
