package com.bloomie.platform.payments.interfaces.rest;

import com.bloomie.platform.payments.application.commanservices.PaymentCommandService;
import com.bloomie.platform.payments.domain.model.commands.ProcessSubscriptionPaymentCommand;
import com.bloomie.platform.subscription.application.commandservices.SubscriptionCommandService;
import com.bloomie.platform.subscription.domain.model.commands.ActivateSubscriptionCommand;
import com.bloomie.platform.subscription.domain.model.commands.SelectSubscriptionPlanCommand;
import com.bloomie.platform.shared.application.result.Result;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller that handles Stripe webhook events.
 * Activates the subscription after a successful payment confirmation.
 */
@RestController
@RequestMapping("/api/v1/webhook/stripe")
@Tag(name = "Stripe Webhook", description = "Stripe webhook event handler")
public class StripeWebhookController {

    private static final Logger log = LoggerFactory.getLogger(StripeWebhookController.class);

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    private final PaymentCommandService      paymentCommandService;
    private final SubscriptionCommandService subscriptionCommandService;

    public StripeWebhookController(PaymentCommandService paymentCommandService,
                                   SubscriptionCommandService subscriptionCommandService) {
        this.paymentCommandService      = paymentCommandService;
        this.subscriptionCommandService = subscriptionCommandService;
    }

    /**
     * Receives Stripe webhook events and processes successful checkout sessions.
     * On checkout.session.completed: creates subscription, registers payment, activates subscription.
     *
     * @param payload   the raw Stripe webhook payload
     * @param sigHeader the Stripe signature header for verification
     * @return 200 OK if processed successfully
     */
    @PostMapping
    @Operation(summary = "Handle Stripe webhook",
            description = "Processes Stripe checkout.session.completed events to activate patient subscriptions.")
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {
        try {
            var event = Webhook.constructEvent(payload, sigHeader, webhookSecret);

            if ("checkout.session.completed".equals(event.getType())) {
                try {
                    var deserializer = event.getDataObjectDeserializer();
                    var rawJson = deserializer.getRawJson();

                    // Parsea directamente con Jackson en vez de Gson
                    var mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                    var sessionNode = mapper.readTree(rawJson);

                    var metadata   = sessionNode.path("metadata");
                    var patientId  = Long.parseLong(metadata.path("patientId").asText());
                    var planId     = Long.parseLong(metadata.path("planId").asText());
                    var amount     = sessionNode.path("amount_total").asLong(0) / 100.0;

                    log.info("Stripe payment completed — patientId={} planId={} amount={}",
                            patientId, planId, amount);

                    var selectResult = subscriptionCommandService.handle(
                            new SelectSubscriptionPlanCommand(patientId, planId));

                    if (selectResult.isSuccess()) {
                        var subscriptionId = ((com.bloomie.platform.shared.application.result.Result.Success<Long, ?>) selectResult).value();

                        paymentCommandService.handle(new ProcessSubscriptionPaymentCommand(
                                patientId, planId, subscriptionId, amount));

                        subscriptionCommandService.handle(
                                new ActivateSubscriptionCommand(patientId, planId));

                        log.info("Subscription activated — patientId={} subscriptionId={}",
                                patientId, subscriptionId);
                    } else {
                        log.warn("Failed to select subscription plan — patientId={} planId={}",
                                patientId, planId);
                    }

                } catch (Exception e) {
                    log.error("Error processing webhook: {}", e.getMessage());
                }
            }

            return ResponseEntity.ok("Webhook processed");

        } catch (SignatureVerificationException e) {
            log.warn("Invalid Stripe webhook signature: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Invalid signature");
        } catch (Exception e) {
            log.error("Webhook processing error: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Webhook error");
        }
    }
}