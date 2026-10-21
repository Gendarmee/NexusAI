package by.gendarme.paymentservice.dto.request;

public class GetHistoryRequest {
    private final Integer UserId;

    public GetHistoryRequest(Integer userId) {
        UserId = userId;
    }

    public Integer getUserId() {
        return UserId;
    }
}
