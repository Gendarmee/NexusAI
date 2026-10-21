package by.gendarme.paymentservice.controller;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionController {
//    private final SubscriptionService subscriptionService;

    @KafkaListener(topics = "user-registrations", groupId = "payment-service-group")
    public void initSubscribeForNewUser() {

    }
}