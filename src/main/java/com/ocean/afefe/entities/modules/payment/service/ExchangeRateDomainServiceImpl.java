package com.ocean.afefe.entities.modules.payment.service;

import com.ocean.afefe.entities.modules.payment.model.ExchangeRate;
import com.ocean.afefe.entities.modules.payment.repository.ExchangeRateRepository;
import com.tensorpoint.toolkit.modules.http.OmnixWebClient;
import com.tensorpoint.toolkit.tpointcore.commons.Country;
import com.tensorpoint.toolkit.tpointcore.commons.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangeRateDomainServiceImpl implements ExchangeRateDomainService{

    private final OmnixWebClient omnixWebClient;
    private final ExchangeRateRepository exchangeRateRepository;

    @Override
    public ExchangeRate getUserCountryExchangeRateWithBaseUSD(Country country){
        return getUserCountryExchangeRate(Currency.USD, country);
    }

    @Override
    public ExchangeRate getUserCountryExchangeRate(Currency baseCurrency, Country country){
        String userCountryCurrencyCode = country.getCurrencyCode().toUpperCase();
        Currency currency = Currency.valueOf(userCountryCurrencyCode);
        Optional<ExchangeRate> exchangeRateOptional = exchangeRateRepository.findTopByFromCurrencyAndToCurrencyOrderByCreatedAt(baseCurrency, currency);
        return exchangeRateOptional.orElseGet(() -> saveNewExchangeRate(currency));
    }

    @Override
    public ExchangeRate getCurrencyExchangeRateWithBaseUSD(Currency currency){
        return getCurrencyExchangeRate(Currency.USD, currency);
    }

    @Override
    public ExchangeRate getCurrencyExchangeRate(Currency baseCurrency, Currency currency){
        Optional<ExchangeRate> exchangeRateOptional = exchangeRateRepository.findTopByFromCurrencyAndToCurrencyOrderByCreatedAt(baseCurrency, currency);
        return exchangeRateOptional.orElseGet(() -> saveNewExchangeRate(currency));
    }

    @Override
    public BigDecimal getUSDValueBasedExchangeRate(BigDecimal amount, Currency currency){
        ExchangeRate exchangeRate = getCurrencyExchangeRateWithBaseUSD(currency);
        BigDecimal exchangeRateValue = exchangeRate.getValue();
        return amount.multiply(exchangeRateValue).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal getBaseCurrencyValueBasedOnExchangeRate(Currency baseCurrency, Currency currency, BigDecimal amount){
        ExchangeRate exchangeRate = getCurrencyExchangeRate(baseCurrency, currency);
        BigDecimal rateValue = exchangeRate.getValue();
        return amount.multiply(rateValue).setScale(2, RoundingMode.HALF_UP);
    }

    public ExchangeRate saveNewExchangeRate(Currency toCurrency){
        return null;
    }

    public ExchangeRate saveNewExchangeRate(Currency from , Currency to){
        return null;
    }
}
