package com.bloomie.platform.payments.application.internal.eventhandlers;

import com.bloomie.platform.dermatologicalappointment.interfaces.events.RequestConsultationPaymentIntegrationEvent;
import com.bloomie.platform.payments.application.commandservices.PaymentCommandService;
import com.bloomie.platform.payments.domain.model.aggregates.Payment;
import com.bloomie.platform.payments.domain.model.commands.ProcessConsultationPaymentCommand;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Policy that processes the consultation payment when a dermatological appointment is scheduled.
 *
 * <p>Listens for {@link RequestConsultationPaymentIntegrationEvent} from the Dermatological
 * Appointment BC, which already carries the dermatologist's consultation fee, and triggers the
 * payment processing flow.</p>
 */
@Service("consultationPaymentRequestedEventHandler")
@Slf4j
public class ConsultationPaymentRequestedEventHandler {

    private final PaymentCommandService paymentCommandService;

    public ConsultationPaymentRequestedEventHandler(PaymentCommandService paymentCommandService) {
        this.paymentCommandService = paymentCommandService;
    }

    @EventListener
    public void on(RequestConsultationPaymentIntegrationEvent event) {
        var command = new ProcessConsultationPaymentCommand(
                event.patientId(),
                event.dermatologistId(),
                event.appointmentId(),
                event.amount());

        var result = paymentCommandService.handle(command);

        if (result.isFailure()) {
            var failure = (Result.Failure<Payment, ApplicationError>) result;
            log.warn("Failed to process consultation payment for appointment {}: code={}, message={}",
                    event.appointmentId(), failure.error().code(), failure.error().message());
        }
    }
}
