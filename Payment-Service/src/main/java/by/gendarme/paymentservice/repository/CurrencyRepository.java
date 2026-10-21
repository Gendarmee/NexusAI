package by.gendarme.paymentservice.repository;

import by.gendarme.paymentservice.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Currency;

public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Integer> {
    CurrencyEntity findByIsoNumeric(Integer isoNumeric);
}
