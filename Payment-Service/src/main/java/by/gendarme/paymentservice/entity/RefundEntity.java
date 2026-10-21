package by.gendarme.paymentservice.entity;

import by.gendarme.paymentservice.entity.enums.RefundStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "refunds")
public class RefundEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "refund_id", nullable = false)
    private UUID id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_id", nullable = false)
    private PaymentEntity paymentEntity;

    @Size(max = 255)
    @NotNull
    @Column(name = "idempotency_key", nullable = false)
    private String idempotencyKey;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Long amount;

    @Size(max = 100)
    @Column(name = "reason", length = 100)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, comment = "refund status not could be null")
    private RefundStatus status = RefundStatus.CREATED;

    @Size(max = 255)
    @Column(name = "provider_refund_id")
    private String providerRefundId;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

    @CreationTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;
}