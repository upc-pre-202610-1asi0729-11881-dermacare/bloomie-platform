package com.bloomie.platform.payments.interfaces.rest;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "PaymentResponse",
        description = "Payment information response",
        example = "{\"id\": 1, \"PatientId\": \"Introduction to Java\", \"description\": \"Learn Java fundamentals and best practices\"}"
)
public class PaymentResource {
}
