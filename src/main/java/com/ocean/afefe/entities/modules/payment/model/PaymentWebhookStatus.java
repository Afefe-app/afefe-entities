package com.ocean.afefe.entities.modules.payment.model;

import com.tensorpoint.toolkit.tpointcore.commons.Enumerable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentWebhookStatus implements Enumerable {
    PENDING("PENDING"),
    CONSUMED("Consumed"),
    FAILED("Failed")
    ;
    private final String description;
}
