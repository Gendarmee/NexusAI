package by.gendarme.paymentservice.entity;

import by.gendarme.paymentservice.entity.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payment_uuid", nullable = false)
    private UUID uuid;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Size(max = 255)
    @NotNull
    @Column(name = "order_id", nullable = false)
    private Integer orderId; // ?

    @Size(max = 255)
    @NotNull
    @Column(name = "idempotency_key", nullable = false, unique = true)
    private String idempotencyKey;

    @Size(max = 255)
    @Column(name = "payer_email")
    private String payerEmail;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    // Универсальные поля
    @Size(max = 50)
    @Column(name = "provider", length = 50)
    private String provider; // "stripe", "yookassa", "paypal"

    @Size(max = 255)
    @Column(name = "provider_payment_id")
    private String providerPaymentId; // pi_..., yk_..., и т.д.

    @Size(max = 255)
    @Column(name = "provider_invoice_id")
    private String providerInvoiceId; // in_..., yk_..., и т.д.

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "provider_raw_response")
    private Map<String, Object> providerRawResponse;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "payment_status not null")
    private PaymentStatus status = PaymentStatus.CREATED;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Long amount;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "currency_id", nullable = false)
    private CurrencyEntity currency;

    // Связь с подпиской (может быть null для разовых платежей)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscribe_uuid")
    private SubscribeEntity subscription;

    @OneToOne(mappedBy = "paymentEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private RefundEntity refund;

    @OneToMany(mappedBy = "paymentEntity", cascade = CascadeType.ALL)
    private List<PaymentEventEntity> events;
}