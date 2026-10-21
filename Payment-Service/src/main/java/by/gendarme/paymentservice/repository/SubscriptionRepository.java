package by.gendarme.paymentservice.repository;

import by.gendarme.paymentservice.entity.SubscribeEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<SubscribeEntity, UUID> {
    SubscribeEntity getSubscribeEntityByUserId(@NotNull Long userId);
}