package by.gendarme.paymentservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubscriptionProviderResponse {
    private String providerSessionId;      // "cs_xxxxx" — ID Checkout Session
    private String providerSubscriptionId; // "sub_xxxxx" — появится после оплаты
    private String providerCustomerId;     // "cus_xxxxx"
    private String status;                 // "active", "canceled", "past_due", "trialing"
    private Long currentPeriodEnd;         // timestamp — когда закончится текущий период
    private String redirectUrl;            // URL на Stripe Checkout
    private Object providerRawResponse;
}