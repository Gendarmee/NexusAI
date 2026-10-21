package by.gendarme.paymentservice.manager;

import by.gendarme.paymentservice.entity.SubscribeEntity;
import by.gendarme.paymentservice.repository.SubscriptionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class SubscriptionDbManager {
    private final SubscriptionRepository subscriptionRepository;

    @Transactional(readOnly = true)
    public SubscribeEntity getSubscriptionForUser(Long userId) {
        return subscriptionRepository.getSubscribeEntityByUserId(userId);
    }

    @Transactional
    public void updateSubscriptionExternalId(UUID subscribeUuid, String externalUuid) {
        SubscribeEntity subscribe = subscriptionRepository.findById(subscribeUuid)
                .orElseThrow(() -> new EntityNotFoundException("Подписка не найдена с id: " + subscribeUuid));
        subscribe.setProviderSubscriptionId(externalUuid);
    }
}
