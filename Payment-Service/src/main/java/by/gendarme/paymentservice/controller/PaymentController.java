package by.gendarme.paymentservice.controller;

import by.gendarme.paymentservice.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * General payment controller for history transactions
 * Not for specific processing payments
 * Not for create payment
 * Only get information about payments
 */
@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<String>> getPaymentHistory() {
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.SERVICE_UNAVAILABLE); // return status code for dev api
    }
}
