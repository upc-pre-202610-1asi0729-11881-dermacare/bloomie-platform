package com.bloomie.platform.payments.interfaces.rest;

import com.bloomie.platform.payments.application.internal.outboundservices.stripe.StripeCheckoutService;
import com.bloomie.platform.payments.interfaces.rest.resources.CheckoutSessionResource;
import com.bloomie.platform.payments.interfaces.rest.resources.CreateCheckoutSessionResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller that creates Stripe Checkout Sessions for subscription payments.
 */
@RestController
@RequestMapping(value = "/api/v1/payments/checkout", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Checkout", description = "Stripe Checkout Session endpoints")
public class CheckoutController {

    private final StripeCheckoutService stripeCheckoutService;

    public CheckoutController(StripeCheckoutService stripeCheckoutService) {
        this.stripeCheckoutService = stripeCheckoutService;
    }

    /**
     * Creates a Stripe Checkout Session and returns the URL to redirect the user to.
     *
     * @param resource the checkout session request payload
     * @return the Stripe Checkout Session URL
     */
    @PostMapping
    @Operation(summary = "Create Stripe Checkout Session",
            description = "Creates a Stripe Checkout Session for a subscription payment and returns the redirect URL.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Checkout session created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid input data."),
            @ApiResponse(responseCode = "500", description = "Failed to create Stripe Checkout Session.")
    })
    public ResponseEntity<CheckoutSessionResource> createCheckoutSession(
            @Valid @RequestBody CreateCheckoutSessionResource resource) {
        var checkoutUrl = stripeCheckoutService.createCheckoutSession(
                resource.patientId(),
                resource.planId(),
                resource.planName(),
                resource.amount()
        );
        return ResponseEntity.ok(new CheckoutSessionResource(checkoutUrl));
    }
}