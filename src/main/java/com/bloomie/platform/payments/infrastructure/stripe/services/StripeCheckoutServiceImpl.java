package com.bloomie.platform.payments.infrastructure.stripe.services;

import com.bloomie.platform.payments.application.internal.outboundservices.stripe.StripeCheckoutService;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Stripe implementation of {@link StripeCheckoutService}.
 * Creates Stripe Checkout Sessions for subscription payments.
 */
@Service
@Slf4j
public class StripeCheckoutServiceImpl implements StripeCheckoutService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Value("${stripe.success.url}")
    private String successUrl;

    @Value("${stripe.cancel.url}")
    private String cancelUrl;

    /**
     * Initializes the Stripe API key after the bean is created.
     */
    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }

    /**
     * Creates a Stripe Checkout Session for a subscription payment.
     *
     * @param patientId the IAM user id of the patient
     * @param planId    the subscription plan identifier
     * @param planName  the display name of the plan
     * @param amount    the payment amount in USD
     * @return the Stripe Checkout Session URL
     */
    @Override
    public String createCheckoutSession(Long patientId, Long planId, String planName, Double amount) {
        try {
            var params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(successUrl + "?session_id={CHECKOUT_SESSION_ID}")
                    .setCancelUrl(cancelUrl)
                    .putMetadata("patientId", patientId.toString())
                    .putMetadata("planId", planId.toString())
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("usd")
                                                    .setUnitAmount((long) (amount * 100))
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName("Bloomie — " + planName)
                                                                    .setDescription("Bloomie skincare subscription plan")
                                                                    .build()
                                                    )
                                                    .build()
                                    )
                                    .build()
                    )
                    .build();

            var session = Session.create(params);
            log.info("Stripe Checkout Session created for patient {} plan {}: {}",
                    patientId, planId, session.getId());
            return session.getUrl();

        } catch (Exception e) {
            log.error("Failed to create Stripe Checkout Session: {}", e.getMessage());
            throw new RuntimeException("stripe.checkout.session.creation.failed");
        }
    }
}