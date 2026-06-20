package com.bloomie.platform.skinanalysis.interfaces.rest;

import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.interfaces.rest.transform.ErrorResponseAssembler;
import com.bloomie.platform.skinanalysis.application.queryservices.SkinAnalysisQueryService;
import com.bloomie.platform.skinanalysis.domain.model.queries.GetSkinAnalysesByPatientIdQuery;
import com.bloomie.platform.skinanalysis.domain.model.queries.GetSkinAnalysisByFacialScanIdQuery;
import com.bloomie.platform.skinanalysis.domain.model.queries.GetSkinAnalysisByIdQuery;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.FacialScanId;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.PatientId;
import com.bloomie.platform.skinanalysis.interfaces.rest.transform.SkinAnalysisResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/skin-analyses", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Skin Analyses", description = "Skin Analysis — Skin Analysis Endpoints")
public class SkinAnalysesController {

    private static final String ANALYSIS_NOT_FOUND = "skin.analysis.not.found";

    private final SkinAnalysisQueryService queryService;

    public SkinAnalysesController(SkinAnalysisQueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping("/{skinAnalysisId}")
    @Operation(summary = "Get a skin analysis by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Skin analysis retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "Skin analysis not found.")})
    public ResponseEntity<?> getSkinAnalysisById(@PathVariable Long skinAnalysisId) {
        var result = queryService.handle(new GetSkinAnalysisByIdQuery(skinAnalysisId));
        if (result.isEmpty()) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("skin-analysis", ANALYSIS_NOT_FOUND));
        }
        return ResponseEntity.ok(SkinAnalysisResourceFromEntityAssembler.toResourceFromEntity(result.get()));
    }

    @GetMapping("/facial-scan/{facialScanId}")
    @Operation(summary = "Get the skin analysis for a facial scan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Skin analysis retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "Skin analysis not found.")})
    public ResponseEntity<?> getSkinAnalysisByFacialScanId(@PathVariable Long facialScanId) {
        var result = queryService.handle(new GetSkinAnalysisByFacialScanIdQuery(new FacialScanId(facialScanId)));
        if (result.isEmpty()) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("skin-analysis", ANALYSIS_NOT_FOUND));
        }
        return ResponseEntity.ok(SkinAnalysisResourceFromEntityAssembler.toResourceFromEntity(result.get()));
    }

    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get all skin analyses for a patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Skin analyses retrieved successfully.")})
    public ResponseEntity<?> getSkinAnalysesByPatientId(@PathVariable Long patientId) {
        var analyses = queryService.handle(new GetSkinAnalysesByPatientIdQuery(new PatientId(patientId)));
        var resources = analyses.stream()
                .map(SkinAnalysisResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}
