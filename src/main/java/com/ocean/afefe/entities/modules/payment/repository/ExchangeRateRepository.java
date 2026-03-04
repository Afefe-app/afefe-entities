package com.ocean.afefe.entities.modules.payment.repository;

import com.ocean.afefe.entities.modules.payment.model.ExchangeRate;
import com.tensorpoint.toolkit.tpointcore.commons.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, UUID> {

    Optional<ExchangeRate> findTopByFromAndToOrderByCreatedAt(Currency from, Currency to);
}
