package by.gendarme.paymentservice.service;

import by.gendarme.paymentservice.dto.request.GetHistoryRequest;
import by.gendarme.paymentservice.dto.request.InitiatePaymentRequest;
import by.gendarme.paymentservice.dto.response.PaymentResponse;
import by.gendarme.paymentservice.entity.CurrencyEntity;
import by.gendarme.paymentservice.entity.PaymentEntity;
import by.gendarme.paymentservice.mapper.PaymentMapper;
import by.gendarme.paymentservice.repository.CurrencyRepository;
import by.gendarme.paymentservice.repository.PaymentRepository;
import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final CurrencyRepository currencyRepository;
    private final StripeClient stripeClient;
    private final PaymentMapper paymentMapper;
    @Autowired
    @Lazy
    private PaymentService proxy;

    public PaymentService(PaymentRepository repository,
                          StripeClient stripeClient,
                          PaymentMapper paymentMapper,
                          PaymentRepository paymentRepository,
                          CurrencyRepository currencyRepository,
                          PaymentMapper paymentMapper1) {
        this.stripeClient = stripeClient;
        this.paymentRepository = paymentRepository;
        this.currencyRepository = currencyRepository;
        this.paymentMapper = paymentMapper1;
    }

    @Transactional
    public PaymentResponse initiatePayment(InitiatePaymentRequest request) {
        CurrencyEntity currency = currencyRepository.findByIsoNumeric(request.getCurrencyIsoNumeric());
        PaymentEntity entity = paymentMapper.toEntity(request);
        
        entity.setCurrency(currency);
        return paymentMapper.toResponse(entity);
    }


    private List<PaymentResponse> getHistoryPayments(GetHistoryRequest request) {
        return paymentRepository.getAllByUserId(request.getUserId());
    }
}