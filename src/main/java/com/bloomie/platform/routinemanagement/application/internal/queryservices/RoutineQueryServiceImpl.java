package com.bloomie.platform.routinemanagement.application.internal.queryservices;

import com.bloomie.platform.routinemanagement.application.queryservices.RoutineQueryService;
import com.bloomie.platform.routinemanagement.domain.model.aggregates.Routine;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetOptionsForSkinTypeAndStep;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetRecommendedProductsForRoutineItemQuery;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetRoutineByIdQuery;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetRoutineByPatientIdQuery;
import com.bloomie.platform.routinemanagement.domain.model.valueobjects.PatientId;
import com.bloomie.platform.routinemanagement.domain.repositories.RoutineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Application service that resolves routine read queries.
 */
@Service
public class RoutineQueryServiceImpl implements RoutineQueryService {

    private static final Map<String, List<String>> PRODUCT_OPTIONS = Map.ofEntries(
            Map.entry("OILY_CLEANSER",          List.of("Gel cleanser", "Foam cleanser", "Salicylic acid cleanser", "Charcoal cleanser")),
            Map.entry("OILY_TONER",             List.of("Balancing toner", "Astringent toner", "Niacinamide toner", "BHA toner")),
            Map.entry("OILY_MOISTURIZER",       List.of("Oil-free moisturizer", "Gel moisturizer", "Water-based moisturizer", "Mattifying moisturizer")),
            Map.entry("OILY_SUNSCREEN",         List.of("SPF 50 sunscreen", "SPF 50+ matte sunscreen", "Oil-free SPF 50", "Gel SPF 50")),
            Map.entry("DRY_CLEANSER",           List.of("Cream cleanser", "Milk cleanser", "Oil cleanser", "Balm cleanser")),
            Map.entry("DRY_TONER",              List.of("Hydrating toner", "Rose water toner", "Ceramide toner", "Glycerin toner")),
            Map.entry("DRY_SERUM",              List.of("Hyaluronic acid serum", "Vitamin E serum", "Squalane serum", "Peptide serum")),
            Map.entry("DRY_MOISTURIZER",        List.of("Rich moisturizer", "Shea butter moisturizer", "Overnight cream", "Barrier repair cream")),
            Map.entry("DRY_SUNSCREEN",          List.of("SPF 30 sunscreen", "Moisturizing SPF 30", "Tinted SPF 30", "Chemical SPF 30")),
            Map.entry("SENSITIVE_CLEANSER",     List.of("Gentle cleanser", "Micellar water", "Calming cleanser", "Fragrance-free cleanser")),
            Map.entry("SENSITIVE_MOISTURIZER",  List.of("Fragrance-free moisturizer", "Oat-based moisturizer", "Calming moisturizer", "Centella moisturizer")),
            Map.entry("SENSITIVE_SUNSCREEN",    List.of("Mineral SPF 30", "Physical SPF 50", "Zinc oxide SPF 30", "Sensitive skin SPF 30")),
            Map.entry("COMBINATION_CLEANSER",   List.of("Balancing cleanser", "Gentle foam cleanser", "pH-balanced cleanser", "Dual-action cleanser")),
            Map.entry("COMBINATION_TONER",      List.of("Pore-minimizing toner", "Balancing toner", "Witch hazel toner", "Niacinamide toner")),
            Map.entry("COMBINATION_MOISTURIZER",List.of("Lightweight moisturizer", "Balancing gel-cream", "Zone control moisturizer", "Hybrid moisturizer")),
            Map.entry("COMBINATION_SUNSCREEN",  List.of("SPF 50 sunscreen", "Lightweight SPF 50", "SPF 50 gel", "Balancing SPF 50")),
            Map.entry("NORMAL_CLEANSER",        List.of("Gentle cleanser", "Foam cleanser", "Gel cleanser", "Cream cleanser")),
            Map.entry("NORMAL_MOISTURIZER",     List.of("Daily moisturizer", "Gel moisturizer", "Lightweight lotion", "Balancing cream")),
            Map.entry("NORMAL_SUNSCREEN",       List.of("SPF 30 sunscreen", "SPF 50 sunscreen", "Tinted SPF 30", "Mineral SPF 30")),
            Map.entry("NORMAL_TONER",           List.of("Balancing toner", "Hydrating toner", "Rose water toner", "Essence toner")),
            Map.entry("NORMAL_SERUM",           List.of("Vitamin C serum", "Niacinamide serum", "Hyaluronic acid serum", "Peptide serum"))
    );

    private final RoutineRepository routineRepository;

    public RoutineQueryServiceImpl(RoutineRepository routineRepository) {
        this.routineRepository = routineRepository;
    }

    @Override
    public Optional<Routine> handle(GetRoutineByIdQuery query) {
        return routineRepository.findById(query.routineId());
    }

    @Override
    public Optional<Routine> handle(GetRoutineByPatientIdQuery query) {
        return routineRepository.findActiveByPatientId(new PatientId(query.patientId()));
    }

    @Override
    public List<String> handle(GetRecommendedProductsForRoutineItemQuery query) {
        var routine = routineRepository.findById(query.routineId());
        if (routine.isEmpty()) return List.of();
        var item = routine.get().getItems().stream()
                .filter(i -> i.getId().equals(query.routineItemId()))
                .findFirst();
        if (item.isEmpty()) return List.of();
        return handle(new GetOptionsForSkinTypeAndStep(routine.get().getSkinType(), item.get().getStep()));
    }

    @Override
    public List<String> handle(GetOptionsForSkinTypeAndStep query) {
        var key = query.skinType() + "_" + query.step();
        return PRODUCT_OPTIONS.getOrDefault(key, List.of());
    }
}
