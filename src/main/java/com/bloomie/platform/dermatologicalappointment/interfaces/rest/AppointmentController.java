package com.bloomie.platform.dermatologicalAppointment.interfaces.rest;

import com.bloomie.platform.dermatologicalAppointment.application.commandservices.AppointmentCommandService;
import com.bloomie.platform.dermatologicalAppointment.application.queryservices.AppointmentQueryService;
import com.bloomie.platform.dermatologicalAppointment.domain.model.queries.GetAppointmentByIdQuery;
import com.bloomie.platform.dermatologicalAppointment.domain.model.queries.GetAppointmentsByDermatologistIdQuery;
import com.bloomie.platform.dermatologicalAppointment.domain.model.queries.GetAppointmentsByPatientIdQuery;
import com.bloomie.platform.dermatologicalAppointment.interfaces.rest.resources.AppointmentResource;
import com.bloomie.platform.dermatologicalAppointment.interfaces.rest.resources.CancelAppointmentResource;
import com.bloomie.platform.dermatologicalAppointment.interfaces.rest.resources.ConfirmAppointmentResource;
import com.bloomie.platform.dermatologicalAppointment.interfaces.rest.resources.ReprogramRequestResource;
import com.bloomie.platform.dermatologicalAppointment.interfaces.rest.resources.ScheduleAppointmentResource;
import com.bloomie.platform.dermatologicalAppointment.interfaces.rest.transform.AppointmentResourceFromEntityAssembler;
import com.bloomie.platform.dermatologicalAppointment.interfaces.rest.transform.CancelAppointmentCommandFromResourceAssembler;
import com.bloomie.platform.dermatologicalAppointment.interfaces.rest.transform.ConfirmAppointmentCommandFromResourceAssembler;
import com.bloomie.platform.dermatologicalAppointment.interfaces.rest.transform.ReprogramRequestCommandFromResourceAssembler;
import com.bloomie.platform.dermatologicalAppointment.interfaces.rest.transform.ScheduleAppointmentCommandFromResourceAssembler;
import com.bloomie.platform.shared.interfaces.rest.transform.ErrorResponseAssembler;
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

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/appointments", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Appointments", description = "Dermatological Appointment Endpoints")
public class AppointmentController {

    private final AppointmentQueryService queryService;
    private final AppointmentCommandService commandService;

    public AppointmentController(AppointmentQueryService queryService,
                                  AppointmentCommandService commandService) {
        this.queryService = queryService;
        this.commandService = commandService;
    }

    @PostMapping
    @Operation(summary = "Schedule a new dermatological appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Appointment scheduled successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid input data."),
            @ApiResponse(responseCode = "404", description = "Patient or dermatologist not found."),
            @ApiResponse(responseCode = "409", description = "Time slot already taken.")})
    public ResponseEntity<?> scheduleAppointment(@Valid @RequestBody ScheduleAppointmentResource resource) {
        var command = ScheduleAppointmentCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result, AppointmentResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an appointment by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "Appointment not found.")})
    public ResponseEntity<?> getAppointmentById(@PathVariable Long id) {
        var result = queryService.handle(new GetAppointmentByIdQuery(id));
        if (result.isEmpty()) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    com.bloomie.platform.shared.application.result.ApplicationError.notFound("appointment", "appointment.not.found"));
        }
        return ResponseEntity.ok(AppointmentResourceFromEntityAssembler.toResourceFromEntity(result.get()));
    }

    @GetMapping
    @Operation(summary = "Get appointments by patient id or dermatologist id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointments retrieved successfully.")})
    public ResponseEntity<List<AppointmentResource>> getAppointments(
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) Long dermatologistId) {
        List<AppointmentResource> resources;
        if (patientId != null) {
            resources = queryService.handle(new GetAppointmentsByPatientIdQuery(patientId))
                    .stream().map(AppointmentResourceFromEntityAssembler::toResourceFromEntity).toList();
        } else if (dermatologistId != null) {
            resources = queryService.handle(new GetAppointmentsByDermatologistIdQuery(dermatologistId))
                    .stream().map(AppointmentResourceFromEntityAssembler::toResourceFromEntity).toList();
        } else {
            resources = Collections.emptyList();
        }
        return ResponseEntity.ok(resources);
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "Cancel an appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment cancelled successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid request or cancellation reason missing."),
            @ApiResponse(responseCode = "404", description = "Appointment not found."),
            @ApiResponse(responseCode = "422", description = "Appointment cannot be cancelled in its current status.")})
    public ResponseEntity<?> cancelAppointment(@PathVariable Long id,
                                                @Valid @RequestBody CancelAppointmentResource resource) {
        var command = CancelAppointmentCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(result, v -> null, HttpStatus.OK);
    }

    @PutMapping("/{id}/reprogram-request")
    @Operation(summary = "Submit a reprogram request for an appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reprogram request submitted successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid input data."),
            @ApiResponse(responseCode = "404", description = "Appointment not found."),
            @ApiResponse(responseCode = "422", description = "Appointment cannot be reprogrammed in its current status.")})
    public ResponseEntity<?> requestReprogram(@PathVariable Long id,
                                               @Valid @RequestBody ReprogramRequestResource resource) {
        var command = ReprogramRequestCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(result, v -> null, HttpStatus.OK);
    }

    @PutMapping("/{id}/confirm")
    @Operation(summary = "Confirm an appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment confirmed successfully."),
            @ApiResponse(responseCode = "404", description = "Appointment not found."),
            @ApiResponse(responseCode = "422", description = "Appointment cannot be confirmed in its current status.")})
    public ResponseEntity<?> confirmAppointment(@PathVariable Long id,
                                                 @Valid @RequestBody ConfirmAppointmentResource resource) {
        var command = ConfirmAppointmentCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result, AppointmentResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.OK);
    }
}
