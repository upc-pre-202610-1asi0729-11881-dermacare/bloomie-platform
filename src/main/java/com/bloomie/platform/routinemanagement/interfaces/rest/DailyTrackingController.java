package com.bloomie.platform.routinemanagement.interfaces.rest;

import com.bloomie.platform.routinemanagement.application.commandservices.DailyTrackingCommandService;
import com.bloomie.platform.routinemanagement.application.queryservices.DailyTrackingQueryService;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetAllDailyTrackingsQuery;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetDailyTrackingsByPatientIdQuery;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetDailyTrackingsByRoutineIdQuery;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetWeeklySummaryByPatientIdQuery;
import com.bloomie.platform.routinemanagement.domain.model.valueobjects.PatientId;
import com.bloomie.platform.routinemanagement.interfaces.rest.resources.DailyTrackingResource;
import com.bloomie.platform.routinemanagement.interfaces.rest.resources.MarkRoutineAsCompletedResource;
import com.bloomie.platform.routinemanagement.interfaces.rest.resources.WeeklySummaryResource;
import com.bloomie.platform.routinemanagement.interfaces.rest.transform.DailyTrackingResourceFromEntityAssembler;
import com.bloomie.platform.routinemanagement.interfaces.rest.transform.MarkRoutineAsCompletedCommandFromResourceAssembler;
import com.bloomie.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller for daily tracking endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/daily-trackings", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Daily Trackings", description = "Routine management daily tracking endpoints")
public class DailyTrackingController {

    private final DailyTrackingCommandService dailyTrackingCommandService;
    private final DailyTrackingQueryService dailyTrackingQueryService;

    public DailyTrackingController(DailyTrackingCommandService dailyTrackingCommandService,
                                   DailyTrackingQueryService dailyTrackingQueryService) {
        this.dailyTrackingCommandService = dailyTrackingCommandService;
        this.dailyTrackingQueryService = dailyTrackingQueryService;
    }

    /**
     * Mark routine as completed for a given date.
     *
     * @param resource the {@link MarkRoutineAsCompletedResource} with patient id, routine id and date
     * @return the identifier of the created daily tracking entry
     */
    @PostMapping
    @Operation(
            summary = "Mark routine as completed",
            description = "Records that a patient completed their skincare routine on a specific date. " +
                    "Only one record is allowed per patient per date, and future dates are not permitted."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Routine marked as completed successfully",
                    content = @Content(schema = @Schema(implementation = Long.class))
            ),
            @ApiResponse(responseCode = "404", description = "Routine not found"),
            @ApiResponse(responseCode = "409", description = "Routine already marked as completed for this date"),
            @ApiResponse(responseCode = "422", description = "Date is in the future")
    })
    public ResponseEntity<?> markRoutineAsCompleted(@RequestBody MarkRoutineAsCompletedResource resource) {
        var command = MarkRoutineAsCompletedCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = dailyTrackingCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                id -> id,
                HttpStatus.CREATED
        );
    }

    /**
     * Get all daily tracking entries for a patient.
     *
     * @param patientId the patient unique identifier
     * @return a list of daily tracking resources for the given patient
     */
    @GetMapping("/patient/{patientId}")
    @Operation(
            summary = "Get daily trackings by patient",
            description = "Retrieves all daily routine completion records for a specific patient."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Daily trackings retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DailyTrackingResource.class)))
            )
    })
    public ResponseEntity<List<DailyTrackingResource>> getDailyTrackingsByPatientId(
            @PathVariable
            @Parameter(description = "Patient unique identifier", example = "1", required = true)
            Long patientId
    ) {
        var query = new GetDailyTrackingsByPatientIdQuery(patientId);
        var trackings = dailyTrackingQueryService.handle(query);
        if (trackings.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        var resources = trackings.stream()
                .map(DailyTrackingResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Get all daily tracking entries for a routine.
     *
     * @param routineId the routine unique identifier
     * @return a list of daily tracking resources for the given routine
     */
    @GetMapping("/routine/{routineId}")
    @Operation(
            summary = "Get daily trackings by routine",
            description = "Retrieves all daily routine completion records associated with a specific routine."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Daily trackings retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DailyTrackingResource.class)))
            )
    })
    public ResponseEntity<List<DailyTrackingResource>> getDailyTrackingsByRoutineId(
            @PathVariable
            @Parameter(description = "Routine unique identifier", example = "1", required = true)
            Long routineId
    ) {
        var query = new GetDailyTrackingsByRoutineIdQuery(routineId);
        var trackings = dailyTrackingQueryService.handle(query);
        if (trackings.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        var resources = trackings.stream()
                .map(DailyTrackingResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Get the weekly routine completion summary for a patient.
     *
     * @param patientId the patient unique identifier
     * @return the {@link WeeklySummaryResource} with the calculated weekly progress
     */
    @GetMapping("/patient/{patientId}/weekly-summary")
    @Operation(
            summary = "Get weekly routine completion summary",
            description = "Returns the number of completed days, missed days and the completion rate " +
                    "for the current week (Monday to Sunday) for a specific patient."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Weekly summary retrieved successfully",
                    content = @Content(schema = @Schema(implementation = WeeklySummaryResource.class))
            )
    })
    public ResponseEntity<WeeklySummaryResource> getWeeklySummary(
            @PathVariable
            @Parameter(description = "Patient unique identifier", example = "1", required = true)
            Long patientId
    ) {
        var query = new GetWeeklySummaryByPatientIdQuery(new PatientId(patientId));
        return ResponseEntity.ok(dailyTrackingQueryService.handle(query));
    }

    /**
     * Get all daily tracking entries.
     *
     * @return a list of all daily tracking resources
     */
    @GetMapping
    @Operation(
            summary = "Get all daily trackings",
            description = "Retrieves all daily routine completion tracking records."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Daily trackings retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DailyTrackingResource.class)))
            )
    })
    public ResponseEntity<List<DailyTrackingResource>> getAllDailyTrackings() {
        var query = new GetAllDailyTrackingsQuery();
        var trackings = dailyTrackingQueryService.handle(query);
        if (trackings.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        var resources = trackings.stream()
                .map(DailyTrackingResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}
