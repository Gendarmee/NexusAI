package by.gendarme.paymentservice.exception;

import com.stripe.exception.StripeException;

public class PaymentProviderException extends Throwable {
    public PaymentProviderException(String stripeCreatePaymentFailed, StripeException e) {
    }
}
