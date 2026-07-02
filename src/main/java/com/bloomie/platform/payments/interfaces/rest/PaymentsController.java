package com.bloomie.platform.payments.interfaces.rest;

import com.bloomie.platform.payments.application.commandservices.PaymentCommandService;
import com.bloomie.platform.payments.application.queryservices.PaymentQueryService;
import com.bloomie.platform.payments.domain.model.queries.GetPaymentByIdQuery;
import com.bloomie.platform.payments.domain.model.queries.GetPaymentsByPatientIdQuery;
import com.bloomie.platform.payments.domain.model.valueobjects.PatientId;
import com.bloomie.platform.payments.domain.model.commands.RefundPaymentCommand;
import com.bloomie.platform.payments.interfaces.rest.resources.PaymentResource;
import com.bloomie.platform.payments.interfaces.rest.resources.ProcessRenewalPaymentResource;
import com.bloomie.platform.payments.interfaces.rest.resources.ProcessSubscriptionPaymentResource;
import com.bloomie.platform.payments.interfaces.rest.transform.PaymentResourceFromEntityAssembler;
import com.bloomie.platform.payments.interfaces.rest.transform.ProcessRenewalPaymentCommandFromResourceAssembler;
import com.bloomie.platform.payments.interfaces.rest.transform.ProcessSubscriptionPaymentCommandFromResourceAssembler;
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

    /**
     * Refund a processed payment.
     *
     * @param paymentId the identifier of the payment to refund
     * @return the updated {@link PaymentResource} with status REFUNDED, or an error response
     */
    @PatchMapping("/{paymentId}/refund")
    @Operation(
            summary = "Refund payment",
            description = "Refunds a processed payment. Only payments in PROCESSED status are eligible."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Payment refunded successfully",
                    content = @Content(schema = @Schema(implementation = PaymentResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Payment cannot be refunded in its current status"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    public ResponseEntity<?> refundPayment(
            @PathVariable
            @Parameter(description = "Unique payment identifier", example = "1", required = true)
            Long paymentId) {
        var command = new RefundPaymentCommand(paymentId);
        var result = paymentCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                PaymentResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    /**
     * Process a renewal payment for an existing active subscription.
     *
     * @param resource the renewal payment request payload
     * @return the created renewal {@link PaymentResource} on success, or an error response
     */
    @PostMapping("/renewal")
    @Operation(
            summary = "Process renewal payment",
            description = "Charges the patient for a subscription renewal. On success, the Subscription BC extends the billing period automatically."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Renewal payment processed successfully",
                    content = @Content(schema = @Schema(implementation = PaymentResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required"),
            @ApiResponse(responseCode = "404", description = "Plan not found")
    })
    public ResponseEntity<?> processRenewalPayment(@RequestBody ProcessRenewalPaymentResource resource) {
        var command = ProcessRenewalPaymentCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = paymentCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                PaymentResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @PostMapping
    @Operation(summary = "Process subscription payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payment processed successfully."),
            @ApiResponse(responseCode = "404", description = "Plan not found.")})
    public ResponseEntity<?> processSubscriptionPayment(
            @RequestBody ProcessSubscriptionPaymentResource resource) {
        var command = ProcessSubscriptionPaymentCommandFromResourceAssembler
                .toCommandFromResource(resource);
        var result = paymentCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                PaymentResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }
}
