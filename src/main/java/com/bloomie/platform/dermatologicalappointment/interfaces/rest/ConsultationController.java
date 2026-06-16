package com.bloomie.platform.dermatologicalAppointment.interfaces.rest;

import com.bloomie.platform.dermatologicalAppointment.application.commandservices.ConsultationCommandService;
import com.bloomie.platform.dermatologicalAppointment.application.queryservices.ConsultationQueryService;
import com.bloomie.platform.dermatologicalAppointment.domain.model.queries.GetConsultationByAppointmentIdQuery;
import com.bloomie.platform.dermatologicalAppointment.domain.model.queries.GetConsultationByIdQuery;
import com.bloomie.platform.dermatologicalAppointment.interfaces.rest.resources.FinishConsultationResource;
import com.bloomie.platform.dermatologicalAppointment.interfaces.rest.resources.RecordDiagnosisResource;
import com.bloomie.platform.dermatologicalAppointment.interfaces.rest.resources.SaveNotesResource;
import com.bloomie.platform.dermatologicalAppointment.interfaces.rest.resources.StartConsultationResource;
import com.bloomie.platform.dermatologicalAppointment.interfaces.rest.resources.UploadClinicalPhotoResource;
import com.bloomie.platform.dermatologicalAppointment.interfaces.rest.transform.ConsultationResourceFromEntityAssembler;
import com.bloomie.platform.dermatologicalAppointment.interfaces.rest.transform.FinishConsultationCommandFromResourceAssembler;
import com.bloomie.platform.dermatologicalAppointment.interfaces.rest.transform.RecordDiagnosisCommandFromResourceAssembler;
import com.bloomie.platform.dermatologicalAppointment.interfaces.rest.transform.SaveNotesCommandFromResourceAssembler;
import com.bloomie.platform.dermatologicalAppointment.interfaces.rest.transform.StartConsultationCommandFromResourceAssembler;
import com.bloomie.platform.dermatologicalAppointment.interfaces.rest.transform.UploadClinicalPhotoCommandFromResourceAssembler;
import com.bloomie.platform.shared.application.result.ApplicationError;
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

@RestController
@RequestMapping(value = "/api/v1/consultations", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Consultations", description = "Dermatological Consultation Endpoints")
public class ConsultationController {

    private final ConsultationQueryService queryService;
    private final ConsultationCommandService commandService;

    public ConsultationController(ConsultationQueryService queryService,
                                   ConsultationCommandService commandService) {
        this.queryService = queryService;
        this.commandService = commandService;
    }

    @PostMapping
    @Operation(summary = "Start a new consultation for a confirmed appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consultation started successfully."),
            @ApiResponse(responseCode = "404", description = "Appointment not found."),
            @ApiResponse(responseCode = "409", description = "Consultation already exists for this appointment."),
            @ApiResponse(responseCode = "422", description = "Appointment is not in CONFIRMED status.")})
    public ResponseEntity<?> startConsultation(@Valid @RequestBody StartConsultationResource resource) {
        var command = StartConsultationCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result, ConsultationResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a consultation by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consultation retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "Consultation not found.")})
    public ResponseEntity<?> getConsultationById(@PathVariable Long id) {
        var result = queryService.handle(new GetConsultationByIdQuery(id));
        if (result.isEmpty()) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("consultation", "consultation.not.found"));
        }
        return ResponseEntity.ok(ConsultationResourceFromEntityAssembler.toResourceFromEntity(result.get()));
    }

    @GetMapping
    @Operation(summary = "Get a consultation by appointment id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consultation retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "Consultation not found.")})
    public ResponseEntity<?> getConsultationByAppointmentId(@RequestParam Long appointmentId) {
        var result = queryService.handle(new GetConsultationByAppointmentIdQuery(appointmentId));
        if (result.isEmpty()) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("consultation", "consultation.not.found"));
        }
        return ResponseEntity.ok(ConsultationResourceFromEntityAssembler.toResourceFromEntity(result.get()));
    }

    @PutMapping("/{id}/save-notes")
    @Operation(summary = "Save clinical notes progressively during a consultation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notes saved successfully."),
            @ApiResponse(responseCode = "404", description = "Consultation not found."),
            @ApiResponse(responseCode = "422", description = "Consultation is not in progress.")})
    public ResponseEntity<?> saveNotes(@PathVariable Long id,
                                        @Valid @RequestBody SaveNotesResource resource) {
        var command = SaveNotesCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result, ConsultationResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.OK);
    }

    @PutMapping("/{id}/diagnosis")
    @Operation(summary = "Record the final diagnosis and recommendations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Diagnosis recorded successfully."),
            @ApiResponse(responseCode = "404", description = "Consultation not found."),
            @ApiResponse(responseCode = "422", description = "Cannot record diagnosis in current status.")})
    public ResponseEntity<?> recordDiagnosis(@PathVariable Long id,
                                              @Valid @RequestBody RecordDiagnosisResource resource) {
        var command = RecordDiagnosisCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result, ConsultationResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.OK);
    }

    @PostMapping("/{id}/photos")
    @Operation(summary = "Upload a clinical photo to a consultation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Photo uploaded successfully."),
            @ApiResponse(responseCode = "400", description = "Photo URL is blank."),
            @ApiResponse(responseCode = "404", description = "Consultation not found."),
            @ApiResponse(responseCode = "422", description = "Cannot upload photo in current status.")})
    public ResponseEntity<?> uploadPhoto(@PathVariable Long id,
                                          @Valid @RequestBody UploadClinicalPhotoResource resource) {
        var command = UploadClinicalPhotoCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result, ConsultationResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/finish")
    @Operation(summary = "Finish and close a consultation session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consultation finished successfully."),
            @ApiResponse(responseCode = "404", description = "Consultation not found."),
            @ApiResponse(responseCode = "422", description = "Consultation cannot be finished in its current status.")})
    public ResponseEntity<?> finishConsultation(@PathVariable Long id,
                                                 @Valid @RequestBody FinishConsultationResource resource) {
        var command = FinishConsultationCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result, ConsultationResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.OK);
    }
}
