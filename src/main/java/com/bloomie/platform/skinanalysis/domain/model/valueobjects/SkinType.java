package com.bloomie.platform.skinAnalysis.domain.model.valueobjects;

/**
 * Fitzpatrick skin type scale classification.
 *
 * <ul>
 *   <li>{@code OILY}   – excess sebum production.</li>
 *   <li>{@code DRY}    – lacks moisture and lipids.</li>
 *   <li>{@code MIXED}  – combination of oily and dry zones.</li>
 *   <li>{@code NORMAL} – balanced, no major concerns.</li>
 *   <li>{@code SENSITIVE} – prone to irritation and reactions.</li>
 * </ul>
 */
public enum SkinType {
    OILY,
    DRY,
    MIXED,
    NORMAL,
    SENSITIVE
}
