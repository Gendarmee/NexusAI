package by.gendarme.paymentservice.repository;

import by.gendarme.paymentservice.dto.response.PaymentResponse;
import by.gendarme.paymentservice.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID> {

    List<PaymentResponse> getAllByUserId(Integer userId);
}