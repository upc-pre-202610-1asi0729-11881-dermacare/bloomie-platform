package com.bloomie.platform.payments.interfaces.rest;

import com.bloomie.platform.payments.application.commanservices.PaymentCommandService;
import com.bloomie.platform.payments.application.queryservices.PaymentQueryService;
import com.bloomie.platform.payments.domain.model.queries.GetPaymentByIdQuery;
import com.bloomie.platform.payments.domain.model.queries.GetPaymentsByPatientIdQuery;
import com.bloomie.platform.payments.domain.model.valueobjects.PatientId;
import com.bloomie.platform.payments.interfaces.rest.resources.PaymentResource;
import com.bloomie.platform.payments.interfaces.rest.transform.PaymentResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * REST controller that exposes payment resources and payment retrieval endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/payments", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Payments", description = "Payment management endpoints")
public class PaymentsController {
    private final PaymentCommandService paymentCommandService;
    private final PaymentQueryService paymentQueryService;

    /**
     * Constructor
     *
     * @param paymentCommandService The {@link PaymentCommandService} instance
     * @param paymentQueryService   The {@link PaymentQueryService} instance
     */
    public PaymentsController(PaymentCommandService paymentCommandService, PaymentQueryService paymentQueryService) {
        this.paymentCommandService = paymentCommandService;
        this.paymentQueryService = paymentQueryService;
    }

    /**
     * Get a payment by payment ID
     *
     * @param paymentId The payment ID
     * @return A {@link PaymentResource} resource for the payment
     */
    @GetMapping("/{paymentId}")
    @Operation(
            summary = "Get payment by Payment ID",
            description = "Retrieves a payment's information by their unique payment identifier."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Payment retrieved successfully",
                    content = @Content(schema = @Schema(implementation = PaymentResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    public ResponseEntity<?> getPaymentById(
            @PathVariable
            @Parameter(description = "Unique payment identifier", example = "1", required = true)
            Long paymentId) {
        var getPaymentByIdQuery = new GetPaymentByIdQuery(paymentId);
        var payment = paymentQueryService.handle(getPaymentByIdQuery);
        if (payment.isEmpty()) return ResponseEntity.notFound().build();
        var paymentEntity = payment.get();
        var paymentResource = PaymentResourceFromEntityAssembler.toResourceFromEntity(paymentEntity);
        return ResponseEntity.ok(paymentResource);
    }

    /**
     * Get all payments for a patient
     *
     * @param patientId The patient ID
     * @return A list of {@link PaymentResource} resources for the patient
     */
    @GetMapping("/patient/{patientId}")
    @Operation(
            summary = "Get payment by patient ID",
            description = "Retrieves all payments for a specific patient."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Payment retrieved successfully",
                    content = @Content(schema = @Schema(implementation = PaymentResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "No payments found for patient")
    })
    public ResponseEntity<?> getPaymentByPatientId(
            @PathVariable
            @Parameter(description = "Patient unique identifier", example = "1", required = true)
            Long patientId
    ) {
        var getPaymentByPatientIdQuery = new GetPaymentsByPatientIdQuery(new PatientId(patientId));
        var payments = paymentQueryService.handle(getPaymentByPatientIdQuery);
        if (payments.isEmpty()) return ResponseEntity.ok(Collections.emptyList());
        return ResponseEntity.ok(payments.stream().map(PaymentResourceFromEntityAssembler::toResourceFromEntity).toList());
    }
}
