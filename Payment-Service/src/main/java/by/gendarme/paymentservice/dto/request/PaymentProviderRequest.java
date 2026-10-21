package by.gendarme.paymentservice.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class PaymentProviderRequest {
    private String priceId;          // "price_xxxxx" — ID цены из Stripe Dashboard
    private String successUrl;       // куда редиректить после успешной оплаты
    private String cancelUrl;        // куда редиректить при отмене
    private String customerEmail;    // опционально, предзаполнит форму
    private Map<String, String> metadata; // ваши доп. данные (userId, orderId и т.д.)
}

