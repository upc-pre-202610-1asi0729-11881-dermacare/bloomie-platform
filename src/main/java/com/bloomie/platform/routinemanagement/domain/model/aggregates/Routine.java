package com.bloomie.platform.routinemanagement.domain.model.aggregates;

import com.bloomie.platform.routinemanagement.domain.model.commands.GeneratePersonalizedRoutineCommand;
import com.bloomie.platform.routinemanagement.domain.model.commands.RemoveProductFromRoutineCommand;
import com.bloomie.platform.routinemanagement.domain.model.commands.ReplaceProductInRoutineCommand;
import com.bloomie.platform.routinemanagement.domain.model.entities.RoutineItem;
import com.bloomie.platform.routinemanagement.domain.model.events.PersonalizedRoutineGeneratedEvent;
import com.bloomie.platform.routinemanagement.domain.model.events.ProductRemovedFromRoutineEvent;
import com.bloomie.platform.routinemanagement.domain.model.events.ProductReplacedInRoutineEvent;
import com.bloomie.platform.routinemanagement.domain.model.valueobjects.PatientId;
import com.bloomie.platform.routinemanagement.domain.model.valueobjects.RoutineStatus;
import com.bloomie.platform.routinemanagement.domain.model.valueobjects.SkinAnalysisId;
import com.bloomie.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Aggregate root representing a personalized skincare routine generated for a patient.
 *
 * <p>Items are generated deterministically based on the patient's skin type.
 * Domain events are published by the repository adapter after each save.</p>
 */
@Getter
public class Routine extends AbstractDomainAggregateRoot<Routine> {

    private Long id;
    private PatientId patientId;
    private SkinAnalysisId skinAnalysisId;
    private RoutineStatus status;
    private String skinType;
    private List<RoutineItem> items;
    private LocalDateTime createdAt;

    /** Steps that are essential to a skincare routine and cannot be removed. */
    private static final List<String> MANDATORY_STEPS = List.of("CLEANSER", "MOISTURIZER", "SUNSCREEN");

    /** Reconstitution constructor — used by the persistence assembler. */
    public Routine() {
    }

    /** Creates a new personalized routine from a command, generating items based on skin type. */
    public Routine(GeneratePersonalizedRoutineCommand command) {
        this.patientId = new PatientId(command.patientId());
        this.skinAnalysisId = new SkinAnalysisId(command.skinAnalysisId());
        this.status = RoutineStatus.ACTIVE;
        this.skinType = command.skinType();
        this.createdAt = LocalDateTime.now();
        this.items = generateItemsForSkinType(command.skinType());
    }

    private List<RoutineItem> generateItemsForSkinType(String skinType) {
        return switch (skinType) {
            case "OILY" -> List.of(
                new RoutineItem(null, "CLEANSER", 1, "AM_PM", "Gel cleanser"),
                new RoutineItem(null, "TONER", 2, "AM_PM", "Balancing toner"),
                new RoutineItem(null, "MOISTURIZER", 3, "AM_PM", "Oil-free moisturizer"),
                new RoutineItem(null, "SUNSCREEN", 4, "AM", "SPF 50 sunscreen")
            );
            case "DRY" -> List.of(
                new RoutineItem(null, "CLEANSER", 1, "AM_PM", "Cream cleanser"),
                new RoutineItem(null, "TONER", 2, "AM_PM", "Hydrating toner"),
                new RoutineItem(null, "SERUM", 3, "AM_PM", "Hyaluronic acid serum"),
                new RoutineItem(null, "MOISTURIZER", 4, "AM_PM", "Rich moisturizer"),
                new RoutineItem(null, "SUNSCREEN", 5, "AM", "SPF 30 sunscreen")
            );
            case "SENSITIVE" -> List.of(
                new RoutineItem(null, "CLEANSER", 1, "AM_PM", "Gentle cleanser"),
                new RoutineItem(null, "MOISTURIZER", 2, "AM_PM", "Fragrance-free moisturizer"),
                new RoutineItem(null, "SUNSCREEN", 3, "AM", "Mineral SPF 30")
            );
            case "COMBINATION" -> List.of(
                new RoutineItem(null, "CLEANSER", 1, "AM_PM", "Balancing cleanser"),
                new RoutineItem(null, "TONER", 2, "AM_PM", "Pore-minimizing toner"),
                new RoutineItem(null, "MOISTURIZER", 3, "AM_PM", "Lightweight moisturizer"),
                new RoutineItem(null, "SUNSCREEN", 4, "AM", "SPF 50 sunscreen")
            );
            default -> List.of(
                new RoutineItem(null, "CLEANSER", 1, "AM_PM", "Gentle cleanser"),
                new RoutineItem(null, "MOISTURIZER", 2, "AM_PM", "Daily moisturizer"),
                new RoutineItem(null, "SUNSCREEN", 3, "AM", "SPF 30 sunscreen")
            );
        };
    }

    /**
     * Removes an optional product step from this routine.
     *
     * <p>Business rules enforced:
     * <ul>
     *   <li>The item must exist in this routine.</li>
     *   <li>The step must not be mandatory (CLEANSER, MOISTURIZER or SUNSCREEN).</li>
     *   <li>The routine must retain at least 2 steps after removal.</li>
     * </ul>
     * </p>
     *
     * @param command command carrying the routine and item identifiers
     * @throws IllegalArgumentException if the item is not found, the step is mandatory,
     *                                  or the minimum item count would be breached
     */
    public void removeItem(RemoveProductFromRoutineCommand command) {
        var item = this.items.stream()
                .filter(i -> i.getId().equals(command.routineItemId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("routine.item.not.found"));

        if (MANDATORY_STEPS.contains(item.getStep()))
            throw new IllegalArgumentException("routine.item.mandatory");

        if (this.items.size() <= 2)
            throw new IllegalArgumentException("routine.minimum.items.reached");

        var removedProduct = item.getProductRecommendation();
        var mutableItems = new ArrayList<>(this.items);
        mutableItems.remove(item);
        this.items = mutableItems;

        registerDomainEvent(ProductRemovedFromRoutineEvent.from(
                this,
                command.routineItemId(),
                removedProduct));
    }

    /** Registers a {@link PersonalizedRoutineGeneratedEvent} after the routine is persisted. */
    public void onGenerated() {
        registerDomainEvent(PersonalizedRoutineGeneratedEvent.from(this));
    }

    public void replaceProduct(ReplaceProductInRoutineCommand command) {
        var item = this.items.stream()
                .filter(i -> i.getId().equals(command.routineItemId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("routine.item.not.found"));

        var previousProduct = item.getProductRecommendation();
        item.updateProductRecommendation(command.newProductRecommendation());

        registerDomainEvent(ProductReplacedInRoutineEvent.from(
                this,
                command.routineItemId(),
                previousProduct,
                command.newProductRecommendation()));
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPatientId(PatientId patientId) {
        this.patientId = patientId;
    }

    public void setSkinAnalysisId(SkinAnalysisId skinAnalysisId) {
        this.skinAnalysisId = skinAnalysisId;
    }

    public void setStatus(RoutineStatus status) {
        this.status = status;
    }

    public void setSkinType(String skinType) {
        this.skinType = skinType;
    }

    public void setItems(List<RoutineItem> items) {
        this.items = items;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /** Returns the primitive patient id from the value object. */
    public Long getPatientId() {
        return patientId.patientId();
    }

    /** Returns the primitive skin analysis id from the value object. */
    public Long getSkinAnalysisId() {
        return skinAnalysisId.skinAnalysisId();
    }

    /** Returns the full {@link PatientId} value object. */
    public PatientId getPatientIdValue() {
        return patientId;
    }

    /** Returns the full {@link SkinAnalysisId} value object. */
    public SkinAnalysisId getSkinAnalysisIdValue() {
        return skinAnalysisId;
    }
}