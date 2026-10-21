package by.gendarme.paymentservice.dto.request;

import lombok.Data;
import lombok.Builder;

import java.util.Map;

@Data
@Builder
public class SubscriptionProviderRequest {
    private String priceId;          // "price_xxxxx" — месячный/годовой план
    private String customerEmail;
    private String successUrl;
    private String cancelUrl;
    private Long trialDays;          // опционально, пробный период
    private Map<String, String> metadata; // userId и т.д.
}
