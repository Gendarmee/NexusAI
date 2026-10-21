package by.gendarme.paymentservice.mapper;

import by.gendarme.paymentservice.dto.request.InitiatePaymentRequest;
import by.gendarme.paymentservice.dto.response.PaymentResponse;
import by.gendarme.paymentservice.entity.PaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updateAt", ignore = true)
//    @Mapping(target = "status", ignore = true)
    PaymentEntity toEntity(InitiatePaymentRequest request);

    PaymentResponse toResponse(PaymentEntity entity);
}