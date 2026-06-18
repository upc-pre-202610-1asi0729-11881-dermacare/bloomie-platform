package com.bloomie.platform.routinemanagement.interfaces.rest;

import com.bloomie.platform.routinemanagement.application.queryservices.RoutineQueryService;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetRoutineByIdQuery;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetRoutineByPatientIdQuery;
import com.bloomie.platform.routinemanagement.interfaces.rest.resources.RoutineResource;
import com.bloomie.platform.routinemanagement.interfaces.rest.transform.RoutineResourceFromEntityAssembler;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.interfaces.rest.transform.ErrorResponseAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller for routine management endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/routines", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Routines", description = "Routine management endpoints")
public class RoutineController {

    private final RoutineQueryService routineQueryService;

    public RoutineController(RoutineQueryService routineQueryService) {
        this.routineQueryService = routineQueryService;
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
}