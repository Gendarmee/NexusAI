package by.gendarme.paymentservice.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateRefundProviderRequest {
    private String providerPaymentId;  // PaymentIntent ID ("pi_xxxxx")
    private Long amount;               // в центах, null = полный возврат
    private String reason;             // "duplicate", "fraudulent", "requested_by_customer"
}