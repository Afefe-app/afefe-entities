package com.ocean.afefe.entities.modules.payment.repository;

import com.ocean.afefe.entities.modules.payment.model.PaymentType;

import java.math.BigDecimal;

public interface PaymentTypeRevenueAggregate {

    PaymentType getPaymentType();

    BigDecimal getTotalAmount();

    Long getTransactionCount();
}
