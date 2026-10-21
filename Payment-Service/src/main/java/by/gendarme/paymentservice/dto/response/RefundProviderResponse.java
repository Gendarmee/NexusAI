package by.gendarme.paymentservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefundProviderResponse {
    private String providerRefundId;   // "re_xxxxx"
    private String status;             // "succeeded", "pending", "failed"
    private Long amount;               // реально возвращённая сумма в центах
    private Object providerRawResponse;
}
