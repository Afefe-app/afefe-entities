package com.ocean.afefe.entities.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tensorpoint.toolkit.tpointcore.commons.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetricCard {

    private BigDecimal amount;
    private Currency currency;
    private BigDecimal percentageChange;

    public static MetricCard from(BigDecimal amount, Currency currency, BigDecimal percentageChange){
        return new MetricCard(amount, currency, percentageChange);
    }

    public static MetricCard from(BigDecimal amount, Currency currency, double percentageChange){
        return MetricCard.from(amount, currency, BigDecimal.valueOf(percentageChange));
    }

    public static MetricCard empty(Currency currency){
        return MetricCard.from(BigDecimal.ZERO, currency, BigDecimal.ZERO);
    }
}
