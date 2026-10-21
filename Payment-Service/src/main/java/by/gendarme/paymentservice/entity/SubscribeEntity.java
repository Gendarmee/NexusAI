package by.gendarme.paymentservice.entity;

import by.gendarme.paymentservice.entity.enums.SubscriptionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "subscribes")
public class SubscribeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "subscribe_uuid", nullable = false)
    private UUID uuid;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "provider", length = 50, nullable = false)
    private String provider; // "stripe", "yookassa", "paypal" и т.д.

    @Column(name = "provider_subscription_id", nullable = false, unique = true)
    private String providerSubscriptionId; // sub_..., yk_..., и т.д.

    @Column(name = "provider_customer_id", nullable = false)
    private String providerCustomerId; // cus_..., yk_..., и т.д.

    @Column(name = "provider_price_id", nullable = false)
    private String providerPriceId; // price_..., yk_..., и т.д.

    // Специфичные данные провайдера в JSON
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "provider_metadata")
    private Map<String, Object> providerMetadata;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "current_period_start")
    private Instant currentPeriodStart;

    @Column(name = "current_period_end")
    private Instant currentPeriodEnd;

    @Column(name = "cancel_at_period_end", nullable = false)
    private Boolean cancelAtPeriodEnd = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SubscriptionStatus status = SubscriptionStatus.ACTIVE;

    @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentEntity> payments = new ArrayList<>();
}