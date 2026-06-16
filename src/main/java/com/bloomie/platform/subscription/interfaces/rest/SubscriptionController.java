package com.bloomie.platform.subscription.interfaces.rest;

import com.bloomie.platform.dermatologicalappointment.domain.model.queries.GetAppointmentsByPatientIdQuery;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;
import com.bloomie.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import com.bloomie.platform.subscription.application.commandservices.SubscriptionCommandService;
import com.bloomie.platform.subscription.application.queryservices.SubscriptionQueryService;
import com.bloomie.platform.subscription.domain.model.queries.GetSubscriptionByIdQuery;
import com.bloomie.platform.subscription.domain.model.queries.GetSubscriptionByPatientIdQuery;
import com.bloomie.platform.subscription.domain.model.valueobjects.PatientId;
import com.bloomie.platform.subscription.interfaces.rest.resources.SelectSubscriptionPlanResource;
import com.bloomie.platform.subscription.interfaces.rest.resources.SubscriptionResource;
import com.bloomie.platform.subscription.interfaces.rest.transform.SelectSubscriptionPlanCommandFromResourceAssembler;
import com.bloomie.platform.subscription.interfaces.rest.transform.SubscriptionResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/subscriptions", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Subscriptions", description = "Subscriptions management endpoints")
public class SubscriptionController {

    private final SubscriptionCommandService subscriptionCommandService;
    private final SubscriptionQueryService subscriptionQueryService;

    public SubscriptionController(SubscriptionCommandService subscriptionCommandService, SubscriptionQueryService subscriptionQueryService) {
        this.subscriptionCommandService = subscriptionCommandService;
        this.subscriptionQueryService = subscriptionQueryService;
    }

    @PostMapping
    @Operation(summary = "Select subscription plan", description = "Creates a new subscription for a patient with the selected plan.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Subscription created successfully",
                    content = @Content(schema = @Schema(implementation = SubscriptionResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions")

    })
    public ResponseEntity<?> selectSubscritionPlan(@RequestBody SelectSubscriptionPlanResource resource) {
        var selectSubscriptionCommand = SelectSubscriptionPlanCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = subscriptionCommandService.handle(selectSubscriptionCommand)
                .flatMap(subscriptionId -> subscriptionQueryService.handle(new GetSubscriptionByIdQuery(subscriptionId))
                        .<Result<com.bloomie.platform.subscription.domain.model.aggregates.Subscription, ApplicationError>>
                                map(Result::success)
                        .orElseGet(() -> Result.failure(ApplicationError.notFound("Subscription", subscriptionId.toString()))));

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                SubscriptionResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{subscriptionId}")
    @Operation(summary = "Get subscription by ID", description = "Retrieves a specific subscription by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Subscription found",
                    content = @Content(schema = @Schema(implementation = SubscriptionResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Subscription not found")
    })
    public ResponseEntity<SubscriptionResource> getSubscriptionById(
            @PathVariable
            @Parameter(description = "Unique subscription identifier", example = "1", required = true)
            Long subscriptionId
    ) {
        var getSubscriptionByIdQuery = new GetSubscriptionByIdQuery(subscriptionId);
        var subscription = subscriptionQueryService.handle(getSubscriptionByIdQuery);
        if (subscription.isEmpty()) return ResponseEntity.notFound().build();
        var subscriptionEntity = subscription.get();
        var subscriptionResource = SubscriptionResourceFromEntityAssembler.toResourceFromEntity(subscriptionEntity);
        return ResponseEntity.ok(subscriptionResource);
    }

    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get subscription by patientId", description = "Retrieves a specific subscription by its patient identifier.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Subscription found",
                    content = @Content(schema = @Schema(implementation = SubscriptionResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Subscription not found")
    })
    public ResponseEntity<SubscriptionResource> getSubscriptionByPatientId(
            @PathVariable
            @Parameter(description = "Unique patient identifier", example = "1", required = true)
            Long patientId
    ) {
        var getSubscriptionByPatientIdQuery = new GetSubscriptionByPatientIdQuery(new PatientId(patientId));
        var subscription = subscriptionQueryService.handle(getSubscriptionByPatientIdQuery);
        if (subscription.isEmpty()) return ResponseEntity.notFound().build();
        var subscriptionEntity = subscription.get();
        var subscriptionResource = SubscriptionResourceFromEntityAssembler.toResourceFromEntity(subscriptionEntity);
        return ResponseEntity.ok(subscriptionResource);
    }
}
