package com.bloomie.platform.skinanalysis.interfaces.rest;

import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.interfaces.rest.transform.ErrorResponseAssembler;
import com.bloomie.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import com.bloomie.platform.skinanalysis.application.commandservices.FacialScanCommandService;
import com.bloomie.platform.skinanalysis.application.queryservices.FacialScanQueryService;
import com.bloomie.platform.skinanalysis.domain.model.queries.GetFacialScanByIdQuery;
import com.bloomie.platform.skinanalysis.domain.model.queries.GetFacialScansByPatientIdQuery;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.PatientId;
import com.bloomie.platform.skinanalysis.interfaces.rest.resources.StartFacialScanResource;
import com.bloomie.platform.skinanalysis.interfaces.rest.transform.FacialScanResourceFromEntityAssembler;
import com.bloomie.platform.skinanalysis.interfaces.rest.transform.StartFacialScanCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/facial-scans", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Facial Scans", description = "Skin Analysis — Facial Scan Endpoints")
public class FacialScansController {

    private static final String FACIAL_SCAN_NOT_FOUND = "skin.facial.scan.not.found";

    private final FacialScanCommandService commandService;
    private final FacialScanQueryService queryService;

    public FacialScansController(FacialScanCommandService commandService,
                                 FacialScanQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Start a facial scan for a patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Facial scan started successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid input data."),
            @ApiResponse(responseCode = "404", description = "Patient not found.")})
    public ResponseEntity<?> startFacialScan(@Valid @RequestBody StartFacialScanResource resource) {
        var command = StartFacialScanCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                id -> FacialScanResourceFromEntityAssembler.toResourceFromEntity(
                        queryService.handle(new GetFacialScanByIdQuery(id)).orElseThrow()),
                HttpStatus.CREATED);
    }

    @GetMapping("/{facialScanId}")
    @Operation(summary = "Get a facial scan by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Facial scan retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "Facial scan not found.")})
    public ResponseEntity<?> getFacialScanById(@PathVariable Long facialScanId) {
        var result = queryService.handle(new GetFacialScanByIdQuery(facialScanId));
        if (result.isEmpty()) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("facial-scan", FACIAL_SCAN_NOT_FOUND));
        }
        return ResponseEntity.ok(FacialScanResourceFromEntityAssembler.toResourceFromEntity(result.get()));
    }

    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get all facial scans for a patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Facial scans retrieved successfully.")})
    public ResponseEntity<?> getFacialScansByPatientId(@PathVariable Long patientId) {
        var scans = queryService.handle(new GetFacialScansByPatientIdQuery(new PatientId(patientId)));
        var resources = scans.stream()
                .map(FacialScanResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}
