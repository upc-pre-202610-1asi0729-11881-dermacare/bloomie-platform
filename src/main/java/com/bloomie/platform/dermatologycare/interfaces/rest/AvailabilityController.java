package com.bloomie.platform.dermatologycare.interfaces.rest;

import com.bloomie.platform.dermatologycare.application.commandservices.AvailabilityCommandService;
import com.bloomie.platform.dermatologycare.application.queryservices.AvailabilityQueryService;
import com.bloomie.platform.dermatologycare.domain.model.queries.GetAvailabilityByDermatologistIdAndDayQuery;
import com.bloomie.platform.dermatologycare.domain.model.queries.GetAvailabilityByDermatologistIdQuery;
import com.bloomie.platform.dermatologycare.domain.model.valueobjects.DermatologistId;
import com.bloomie.platform.dermatologycare.interfaces.rest.resources.AvailabilityResource;
import com.bloomie.platform.dermatologycare.interfaces.rest.resources.DefineAvailabilityResource;
import com.bloomie.platform.dermatologycare.interfaces.rest.resources.UpdateAvailabilityResource;
import com.bloomie.platform.dermatologycare.interfaces.rest.transform.AvailabilityResourceFromEntityAssembler;
import com.bloomie.platform.dermatologycare.interfaces.rest.transform.DefineAvailabilityCommandFromResourceAssembler;
import com.bloomie.platform.dermatologycare.interfaces.rest.transform.UpdateAvailabilityCommandFromResourceAssembler;
import com.bloomie.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/availabilities", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Availabilities", description = "Availability Endpoints")
public class AvailabilityController {

    private final AvailabilityQueryService queryService;
    private final AvailabilityCommandService commandService;

    public AvailabilityController(AvailabilityQueryService queryService,
                                  AvailabilityCommandService commandService) {
        this.queryService = queryService;
        this.commandService = commandService;
    }

    @GetMapping
    @Operation(summary = "Get availabilities by dermatologist, optionally filtered by day")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Availabilities retrieved successfully.")})
    public ResponseEntity<List<AvailabilityResource>> getAvailabilities(
            @RequestParam Long dermatologistId,
            @RequestParam(required = false) String day) {
        List<AvailabilityResource> resources;
        if (day != null && !day.isBlank()) {
            var query = new GetAvailabilityByDermatologistIdAndDayQuery(
                    new DermatologistId(dermatologistId),
                    DayOfWeek.valueOf(day.toUpperCase()));
            resources = queryService.handle(query).stream()
                    .map(AvailabilityResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();
        } else {
            var query = new GetAvailabilityByDermatologistIdQuery(new DermatologistId(dermatologistId));
            resources = queryService.handle(query).stream()
                    .map(AvailabilityResourceFromEntityAssembler::toResourceFromEntity)
                    .toList();
        }
        return ResponseEntity.ok(resources.isEmpty() ? Collections.emptyList() : resources);
    }

    @PostMapping
    @Operation(summary = "Define a new availability slot")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Availability defined successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid input data."),
            @ApiResponse(responseCode = "409", description = "Availability already defined for this day.")})
    public ResponseEntity<?> defineAvailability(@Valid @RequestBody DefineAvailabilityResource resource) {
        var command = DefineAvailabilityCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                AvailabilityResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED);
    }

    @PutMapping("/{availabilityId}")
    @Operation(summary = "Update an availability slot")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Availability updated successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid input data."),
            @ApiResponse(responseCode = "404", description = "Availability not found.")})
    public ResponseEntity<?> updateAvailability(
            @PathVariable Long availabilityId,
            @Valid @RequestBody UpdateAvailabilityResource resource) {
        var command = UpdateAvailabilityCommandFromResourceAssembler
                .toCommandFromResource(availabilityId, resource);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                AvailabilityResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK);
    }
}
