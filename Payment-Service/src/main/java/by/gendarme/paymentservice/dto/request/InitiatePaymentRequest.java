package by.gendarme.paymentservice.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class InitiatePaymentRequest {
    private Integer orderId;
    private Integer userId;
    private String payerEmail;
    private Long amount;
    private Integer CurrencyIsoNumeric;
    private String idempotencyKey;
    private String provider;
}