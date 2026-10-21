package by.gendarme.paymentservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InitializeSubscribesResponse {
    private final Long userId;
}