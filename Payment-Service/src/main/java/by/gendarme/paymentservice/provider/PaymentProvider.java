package by.gendarme.paymentservice.provider;

import by.gendarme.paymentservice.dto.request.CreateRefundProviderRequest;
import by.gendarme.paymentservice.dto.request.PaymentProviderRequest;
import by.gendarme.paymentservice.dto.request.SubscriptionProviderRequest;
import by.gendarme.paymentservice.dto.response.PaymentProviderResponse;
import by.gendarme.paymentservice.dto.response.RefundProviderResponse;
import by.gendarme.paymentservice.dto.response.SubscriptionProviderResponse;
import by.gendarme.paymentservice.exception.PaymentProviderException;
import by.gendarme.paymentservice.exception.WebhookVerificationException;
import by.gendarme.paymentservice.provider.event.WebhookEvent;

public interface PaymentProvider {
    String getProviderName();

    PaymentProviderResponse createPayment(PaymentProviderRequest request) throws PaymentProviderException;
    PaymentProviderResponse getPaymentStatus(String providerPaymentId) throws PaymentProviderException;

    RefundProviderResponse createRefund(CreateRefundProviderRequest request) throws PaymentProviderException;

    WebhookEvent parseWebhook(String payload, String signature) throws WebhookVerificationException;

    SubscriptionProviderResponse createSubscription(SubscriptionProviderRequest request) throws PaymentProviderException;
    SubscriptionProviderResponse cancelSubscription(String providerSubscriptionId) throws PaymentProviderException;
    SubscriptionProviderResponse getSubscriptionStatus(String providerSubscriptionId) throws PaymentProviderException;
}
