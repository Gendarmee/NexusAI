package by.gendarme.paymentservice.controller;

import by.gendarme.paymentservice.provider.StripePaymentProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stripe/webhook")
public class StripeWebhookController {
    private final StripePaymentProvider provider;

    public StripeWebhookController(StripePaymentProvider provider) {
        this.provider = provider;
    }

    @PostMapping
    public ResponseEntity<String> post() {
        System.out.println("post");
        return ResponseEntity.ok("");

    }

    @GetMapping
    public ResponseEntity<String> get() {
        System.out.println("get");
        return ResponseEntity.ok("");
    }
}
