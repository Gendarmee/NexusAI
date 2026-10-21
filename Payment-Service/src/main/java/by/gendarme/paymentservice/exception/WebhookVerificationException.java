package by.gendarme.paymentservice.exception;

import com.stripe.exception.SignatureVerificationException;

public class WebhookVerificationException extends Throwable {
    public WebhookVerificationException(String invalidStripeWebhookSignature, SignatureVerificationException e) {
    }
}
