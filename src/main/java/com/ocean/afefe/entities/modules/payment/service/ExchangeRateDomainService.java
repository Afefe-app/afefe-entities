package com.ocean.afefe.entities.modules.payment.service;

import com.ocean.afefe.entities.modules.payment.model.ExchangeRate;
import com.tensorpoint.toolkit.tpointcore.commons.Country;
import com.tensorpoint.toolkit.tpointcore.commons.Currency;

import java.math.BigDecimal;

public interface ExchangeRateDomainService {
    ExchangeRate getUserCountryExchangeRateWithBaseUSD(Country country);

    ExchangeRate getUserCountryExchangeRate(Currency baseCurrency, Country country);

    ExchangeRate getCurrencyExchangeRateWithBaseUSD(Currency currency);

    ExchangeRate getCurrencyExchangeRate(Currency baseCurrency, Currency currency);

    BigDecimal getUSDValueBasedExchangeRate(BigDecimal amount, Currency currency);

    BigDecimal getBaseCurrencyValueBasedOnExchangeRate(Currency baseCurrency, Currency currency, BigDecimal amount);
}
