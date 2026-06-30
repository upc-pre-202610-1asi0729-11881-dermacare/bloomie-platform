package com.bloomie.platform.subscription.interfaces.rest;

import com.bloomie.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import com.bloomie.platform.subscription.application.queryservices.SubscriptionQueryService;
import com.bloomie.platform.subscription.domain.model.queries.GetAllPlansQuery;
import com.bloomie.platform.subscription.domain.model.queries.GetPlanByIdQuery;
import com.bloomie.platform.subscription.interfaces.rest.resources.PlanResource;
import com.bloomie.platform.subscription.interfaces.rest.transform.PlanResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(value = "/api/v1/plans", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Plans", description = "Subscription plan endpoints")
public class PlanController {
    private final SubscriptionQueryService subscriptionQueryService;

    public PlanController(SubscriptionQueryService subscriptionQueryService) {
        this.subscriptionQueryService = subscriptionQueryService;
    }

    @GetMapping
    @Operation(summary = "Get all plans", description = "Retrieves a list of all available plans.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Plan retrieved successfully",
                    content = @Content(schema = @Schema(implementation = PlanResource.class))
            )
    })
    public ResponseEntity<List<PlanResource>> getAllPlans() {
        var plans = subscriptionQueryService.handle(new GetAllPlansQuery());
        var planResources = plans.stream()
                .map(PlanResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(planResources);
    }

    @GetMapping("/{planId}")
    @Operation(summary = "Get plan by ID", description = "Retrieves a specific plan by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Plan found",
                    content = @Content(schema = @Schema(implementation = PlanResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Plan not found")
    })
    public ResponseEntity<PlanResource> getPlanById(
            @PathVariable
            @Parameter(description = "Unique plan identifier", example = "1", required = true)
            Long planId
    ) {
        var getPlanByIdQuery = new GetPlanByIdQuery(planId);
        var plan = subscriptionQueryService.handle(getPlanByIdQuery);
        if (plan.isEmpty()) return ResponseEntity.notFound().build();
        var planEntity = plan.get();
        var planResource = PlanResourceFromEntityAssembler.toResourceFromEntity(planEntity);
        return ResponseEntity.ok(planResource);
    }

}
