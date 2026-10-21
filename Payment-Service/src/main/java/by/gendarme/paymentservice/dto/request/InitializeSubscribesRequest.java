package by.gendarme.paymentservice.dto.request;

import lombok.Data;

@Data
public class InitializeSubscribesRequest {
    private Long userId;
    private Long amount;

}