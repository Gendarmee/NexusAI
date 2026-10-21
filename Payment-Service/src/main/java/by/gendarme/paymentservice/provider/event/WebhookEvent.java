package by.gendarme.paymentservice.provider.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WebhookEvent {
    private String eventId;    // для idempotency key
    private String eventType;  // "invoice.paid", "customer.subscription.deleted" и т.д.
    private Object rawData;    // оригинальный event
}