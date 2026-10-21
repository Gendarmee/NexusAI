package by.gendarme.paymentservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentProviderResponse {
    private String providerPaymentId;  // ID сессии/платежа в Stripe ("cs_xxxxx")
    private String status;             // "open", "complete", "expired"
    private String redirectUrl;        // URL на Stripe Checkout — отдаёте фронтенду
    private Object providerRawResponse; // сырой объект от Stripe (Session, PaymentIntent)
}
