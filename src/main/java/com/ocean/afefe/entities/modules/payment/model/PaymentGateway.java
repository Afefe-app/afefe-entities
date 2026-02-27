package com.ocean.afefe.entities.modules.payment.model;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentGateway implements Enumerable {

    STRIPE("Stripe"),
    PAYSTACK("Paystack")
    ;
    private final String description;
}
