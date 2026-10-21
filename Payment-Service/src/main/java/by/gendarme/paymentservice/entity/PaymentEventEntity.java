package by.gendarme.paymentservice.entity;

import by.gendarme.paymentservice.entity.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "payment_events")
public class PaymentEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "event_id", nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_id", nullable = false)
    private PaymentEntity paymentEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_status", columnDefinition = "payment_status not null")
    private PaymentStatus eventStatus = PaymentStatus.CREATED;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "payload")
    private Map<String, Object> payload;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;
}