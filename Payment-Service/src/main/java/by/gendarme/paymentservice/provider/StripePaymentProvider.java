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
import com.stripe.StripeClient;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.Refund;
import com.stripe.model.Subscription;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.RefundCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class StripePaymentProvider implements PaymentProvider {
    private final StripeClient stripeClient;

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    public StripePaymentProvider(@Value("${stripe.secret.key}") String secretKey) {
        this.stripeClient = new StripeClient(secretKey);
    }

    @Override
    public String getProviderName() {
        return "stripe";
    }

    @Override
    public PaymentProviderResponse createPayment(PaymentProviderRequest request) throws PaymentProviderException {
        try {
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(request.getSuccessUrl())
                    .setCancelUrl(request.getCancelUrl())
                    .addLineItem(SessionCreateParams.LineItem.builder()
                            .setPrice(request.getPriceId())
                            .setQuantity(1L)
                            .build())
                    .build();

            Session session = stripeClient.v1().checkout().sessions().create(params);

            return PaymentProviderResponse.builder()
                    .providerPaymentId(session.getId())
                    .status(session.getStatus())
                    .redirectUrl(session.getUrl())
                    .providerRawResponse(session)
                    .build();
        } catch (StripeException e) {
            throw new PaymentProviderException("Stripe createPayment failed", e);
        }
    }


    @Override
    public PaymentProviderResponse getPaymentStatus(String providerPaymentId) throws PaymentProviderException {
        try {
            Session session = stripeClient.v1().checkout().sessions().retrieve(providerPaymentId);

            return PaymentProviderResponse.builder()
                    .providerPaymentId(session.getId())
                    .status(session.getPaymentStatus())
                    .providerRawResponse(session)
                    .build();
        } catch (StripeException e) {
            throw new PaymentProviderException("Stripe getPaymentStatus failed", e);
        }
    }

    @Override
    public RefundProviderResponse createRefund(CreateRefundProviderRequest request) throws PaymentProviderException {
        try {
            RefundCreateParams params = RefundCreateParams.builder()
                    .setPaymentIntent(request.getProviderPaymentId())
                    .setAmount(request.getAmount()) // null = полный возврат
                    .build();

            Refund refund = stripeClient.v1().refunds().create(params);

            return RefundProviderResponse.builder()
                    .providerRefundId(refund.getId())
                    .status(refund.getStatus())
                    .amount(refund.getAmount())
                    .providerRawResponse(refund)
                    .build();
        } catch (StripeException e) {
            throw new PaymentProviderException("Stripe createRefund failed", e);
        }
    }


    @Override
    public WebhookEvent parseWebhook(String payload, String signature) throws WebhookVerificationException {
        try {
            Event event = Webhook.constructEvent(payload, signature, webhookSecret);

            return WebhookEvent.builder()
                    .eventId(event.getId())
                    .eventType(event.getType())
                    .rawData(event)
                    .build();
        } catch (SignatureVerificationException e) {
            throw new WebhookVerificationException("Invalid Stripe webhook signature", e);
        }
    }


    @Override
    public SubscriptionProviderResponse createSubscription(SubscriptionProviderRequest request) throws PaymentProviderException {
        try {
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                    .setSuccessUrl(request.getSuccessUrl())
                    .setCancelUrl(request.getCancelUrl())
                    .setCustomerEmail(request.getCustomerEmail())
                    .addLineItem(SessionCreateParams.LineItem.builder()
                            .setPrice(request.getPriceId())
                            .setQuantity(1L)
                            .build())
                    .build();

            Session session = stripeClient.v1().checkout().sessions().create(params);

            return SubscriptionProviderResponse.builder()
                    .providerSessionId(session.getId())
                    .status(session.getStatus())
                    .redirectUrl(session.getUrl())
                    .providerRawResponse(session)
                    .build();
        } catch (StripeException e) {
            throw new PaymentProviderException("Stripe createSubscription failed", e);
        }
    }


    @Override
    public SubscriptionProviderResponse cancelSubscription(String providerSubscriptionId) throws PaymentProviderException {
        try {
            Subscription subscription = stripeClient.v1().subscriptions().cancel(providerSubscriptionId);

            return SubscriptionProviderResponse.builder()
                    .providerSubscriptionId(subscription.getId())
                    .status(subscription.getStatus()) // "canceled"
                    .providerRawResponse(subscription)
                    .build();
        } catch (StripeException e) {
            throw new PaymentProviderException("Stripe cancelSubscription failed", e);
        }
    }


    @Override
    public SubscriptionProviderResponse getSubscriptionStatus(String providerSubscriptionId) throws PaymentProviderException {
        try {
            Subscription subscription = stripeClient.v1().subscriptions().retrieve(providerSubscriptionId);
            Long currentPeriodEnd = subscription.getItems().getData().getFirst().getCurrentPeriodEnd();

            return SubscriptionProviderResponse.builder()
                    .providerSubscriptionId(subscription.getId())
                    .status(subscription.getStatus())
                    .currentPeriodEnd(currentPeriodEnd)
                    .providerRawResponse(subscription)
                    .build();
        } catch (StripeException e) {
            throw new PaymentProviderException("Stripe getSubscriptionStatus failed", e);
        }
    }
}
