package by.gendarme.paymentservice.service;

import by.gendarme.paymentservice.manager.SubscriptionDbManager;
import by.gendarme.paymentservice.provider.PaymentProvider;
import by.gendarme.paymentservice.repository.SubscriptionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubscriptionService {
    private final PaymentService paymentService;
    private final SubscriptionRepository subscriptionRepository;
    private final PaymentProvider provider;
    private final SubscriptionDbManager dbManager;

    // Kafka events
    // 1. Create subscription for new user with trial (stripe) (Kafka event)
    // 2. Buy different product (subscription) (Rest)
    // 3. Cancel subscription (off automation paying) (Rest)
    // 4. Make refund and cancel (Rest)
    // 5. Get information by subscription (Rest)
    // 6. ?Make discount for new users for first buy (Kafka event)
    // 7. Lower price when switching to a new subscription
    // X. Admin panel for edit product (maybe Billing Service)

}