package com.ocean.afefe.entities.common;

import com.tensorpoint.toolkit.tpointcore.commons.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record Money(BigDecimal amount, Currency currency) {

    private static final int MONEY_ROUNDING_MODE_SCALE = 2;

    public Money {
        if (Objects.isNull(amount)) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if (Objects.isNull(currency)) {
            throw new IllegalArgumentException("Currency cannot be null");
        }
        amount = amount.setScale(MONEY_ROUNDING_MODE_SCALE, RoundingMode.HALF_UP);
    }

    public static Money of(BigDecimal amount, Currency currency) {
        return new Money(amount, currency);
    }

    public Money add(BigDecimal amount) {
        return new Money(this.amount().add(amount), this.currency());
    }

    public Money add(Double amount) {
        return add(BigDecimal.valueOf(amount));
    }

    public Money add(Money money) {
        ensureSameCurrency(money.currency());
        return add(money.amount());
    }

    public Money subtract(BigDecimal amount) {
        return new Money(this.amount().subtract(amount), this.currency());
    }

    public Money subtract(Double amount) {
        return subtract(BigDecimal.valueOf(amount));
    }

    public Money subtract(Money money) {
        ensureSameCurrency(money.currency());
        return subtract(money.amount());
    }

    public void ensureSameCurrency(Currency currency) {
        if (!this.currency().equals(currency)) {
            throw new IllegalArgumentException(
                    "Currency mismatch: %s vs %s".formatted(this.currency(), currency));
        }
    }
}
