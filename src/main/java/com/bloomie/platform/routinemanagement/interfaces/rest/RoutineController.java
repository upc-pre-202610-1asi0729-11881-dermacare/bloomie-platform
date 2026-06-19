package com.bloomie.platform.routinemanagement.interfaces.rest;

import com.bloomie.platform.routinemanagement.application.commandservices.RoutineCommandService;
import com.bloomie.platform.routinemanagement.application.queryservices.RoutineQueryService;
import com.bloomie.platform.routinemanagement.domain.model.commands.RemoveProductFromRoutineCommand;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetRecommendedProductsForRoutineItemQuery;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetRoutineByIdQuery;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetRoutineByPatientIdQuery;
import com.bloomie.platform.routinemanagement.interfaces.rest.resources.ReplaceProductInRoutineResource;
import com.bloomie.platform.routinemanagement.interfaces.rest.resources.RoutineResource;
import com.bloomie.platform.routinemanagement.interfaces.rest.transform.ReplaceProductInRoutineCommandFromResourceAssembler;
import com.bloomie.platform.routinemanagement.interfaces.rest.transform.RoutineResourceFromEntityAssembler;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.interfaces.rest.transform.ErrorResponseAssembler;
import com.bloomie.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
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

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller for routine management endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/routines", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Routines", description = "Routine management endpoints")
public class RoutineController {

    private final RoutineQueryService routineQueryService;
    private final RoutineCommandService routineCommandService;

    public RoutineController(RoutineQueryService routineQueryService,
                             RoutineCommandService routineCommandService) {
        this.routineQueryService = routineQueryService;
        this.routineCommandService = routineCommandService;
    }

    @GetMapping("/patient/{patientId}")
    @Operation(
            summary = "Get active routine by patient",
            description = "Retrieves the active personalized skincare routine for the given patient."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Routine found",
                    content = @Content(schema = @Schema(implementation = RoutineResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Routine not found")
    })
    public ResponseEntity<?> getRoutineByPatientId(
            @PathVariable
            @Parameter(description = "Patient unique identifier", example = "1", required = true)
            Long patientId
    ) {
        var query = new GetRoutineByPatientIdQuery(patientId);
        var routine = routineQueryService.handle(query);
        if (routine.isEmpty()) {
            var error = ApplicationError.notFound("Routine", patientId.toString());
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(error);
        }
        return ResponseEntity.ok(RoutineResourceFromEntityAssembler.toResourceFromEntity(routine.get()));
    }

    @GetMapping("/{routineId}")
    @Operation(
            summary = "Get routine by ID",
            description = "Retrieves a specific personalized skincare routine by its unique identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Routine found",
                    content = @Content(schema = @Schema(implementation = RoutineResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Routine not found")
    })
    public ResponseEntity<?> getRoutineById(
            @PathVariable
            @Parameter(description = "Routine unique identifier", example = "1", required = true)
            Long routineId
    ) {
        var query = new GetRoutineByIdQuery(routineId);
        var routine = routineQueryService.handle(query);
        if (routine.isEmpty()) {
            var error = ApplicationError.notFound("Routine", routineId.toString());
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(error);
        }
        return ResponseEntity.ok(RoutineResourceFromEntityAssembler.toResourceFromEntity(routine.get()));
    }

    @GetMapping("/{routineId}/items/{routineItemId}/replacement-options")
    @Operation(
            summary = "Get product replacement options for a routine item",
            description = "Returns the 4 recommended product alternatives for the given routine item based on the patient's skin type."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Options retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Routine or item not found")
    })
    public ResponseEntity<List<String>> getReplacementOptions(
            @PathVariable
            @Parameter(description = "Routine unique identifier", example = "1", required = true)
            Long routineId,
            @PathVariable
            @Parameter(description = "Routine item unique identifier", example = "1", required = true)
            Long routineItemId
    ) {
        var query = new GetRecommendedProductsForRoutineItemQuery(routineId, routineItemId);
        var options = routineQueryService.handle(query);
        return ResponseEntity.ok(options);
    }

    @DeleteMapping("/{routineId}/items/{routineItemId}")
    @Operation(
            summary = "Remove a product step from a routine",
            description = "Removes an optional product step from the patient's active routine. " +
                    "Mandatory steps (CLEANSER, MOISTURIZER, SUNSCREEN) cannot be removed " +
                    "and the routine must retain at least 2 steps."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product step removed successfully",
                    content = @Content(schema = @Schema(implementation = RoutineResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Routine not found"),
            @ApiResponse(responseCode = "422", description = "Step is mandatory, minimum items reached, or item not found")
    })
    public ResponseEntity<?> removeProductFromRoutineItem(
            @PathVariable
            @Parameter(description = "Routine unique identifier", example = "1", required = true)
            Long routineId,
            @PathVariable
            @Parameter(description = "Routine item unique identifier", example = "1", required = true)
            Long routineItemId
    ) {
        var command = new RemoveProductFromRoutineCommand(routineId, routineItemId);
        var result = routineCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                RoutineResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @PutMapping("/{routineId}/items/{routineItemId}/replace")
    @Operation(
            summary = "Replace product in a routine item",
            description = "Replaces the current product recommendation for a routine item with a new one from the recommended options."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product replaced successfully",
                    content = @Content(schema = @Schema(implementation = RoutineResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Routine not found"),
            @ApiResponse(responseCode = "422", description = "Product is not a recommended option or item not found")
    })
    public ResponseEntity<?> replaceProductInRoutineItem(
            @PathVariable
            @Parameter(description = "Routine unique identifier", example = "1", required = true)
            Long routineId,
            @PathVariable
            @Parameter(description = "Routine item unique identifier", example = "1", required = true)
            Long routineItemId,
            @RequestBody ReplaceProductInRoutineResource resource
    ) {
        var command = ReplaceProductInRoutineCommandFromResourceAssembler
                .toCommandFromResource(routineId, routineItemId, resource);
        var result = routineCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                RoutineResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }
}
